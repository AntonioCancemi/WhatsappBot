package com.whatsbot.auth.service;

import com.whatsbot.auth.dto.LoginRequest;
import com.whatsbot.auth.dto.LoginResponse;
import com.whatsbot.auth.dto.RegisterRequest;
import com.whatsbot.auth.dto.RoleDto;
import com.whatsbot.auth.dto.UserDto;
import com.whatsbot.auth.exception.AuthException;
import com.whatsbot.auth.exception.UnauthorizedException;
import com.whatsbot.auth.mapper.AuthUserMapper;
import com.whatsbot.auth.mapper.RoleMapper;
import com.whatsbot.auth.model.AuthUser;
import com.whatsbot.auth.model.Role;
import com.whatsbot.auth.model.Tenant;
import com.whatsbot.auth.repository.AuthUserRepository;
import com.whatsbot.auth.repository.RoleRepository;
import com.whatsbot.auth.repository.TenantRepository;
import com.whatsbot.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthUserMapper mapper;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final TenantRepository tenantRepository;

    public LoginResponse login(LoginRequest request) {
        AuthUser user = userRepository.findByUsernameAndTenant_Name(request.username(), request.tenant())
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getTenant().getId());
        return new LoginResponse(token);
    }

    public LoginResponse register(RegisterRequest request) {
        Tenant tenant = tenantRepository.findByName(request.tenant())
                .orElseGet(() -> tenantRepository.save(new Tenant(null, request.tenant())));

        if (userRepository.findByUsernameAndTenant_Name(request.username(), request.tenant()).isPresent()) {
            throw new AuthException("User already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new AuthException("Role not found"));

        AuthUser user = new AuthUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setTenant(tenant);
        user.setRoles(Set.of(role));
        user = userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getUsername(), tenant.getId());
        return new LoginResponse(token);
    }

    public List<RoleDto> listRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).toList();
    }

    public UserDto currentUser(String username, UUID tenantId) {
        AuthUser user = userRepository.findByUsernameAndTenant_Id(username, tenantId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        return mapper.toDto(user);
    }

    public UserDto toDto(AuthUser user) {
        return mapper.toDto(user);
    }
}
