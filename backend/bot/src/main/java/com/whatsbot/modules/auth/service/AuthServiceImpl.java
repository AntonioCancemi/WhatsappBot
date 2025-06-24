package com.whatsbot.modules.auth.service;

import com.whatsbot.modules.auth.dto.*;
import com.whatsbot.modules.auth.exception.AuthException;
import com.whatsbot.modules.auth.exception.UnauthorizedException;
import com.whatsbot.modules.auth.mapper.UserMapper;
import com.whatsbot.modules.auth.mapper.RoleMapper;
import com.whatsbot.modules.auth.model.*;
import com.whatsbot.modules.auth.repository.*;
import com.whatsbot.modules.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper mapper;
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final TenantRepository tenantRepository;

    @Override
    public LoginResponse login(LoginRequest request, UUID tenantId) {
        User user = userRepository.findByEmailAndTenant_Id(request.email(), tenantId)
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getId(), user.getEmail(), tenantId);
        return new LoginResponse(token, mapper.toDto(user));
    }

    @Override
    public RegisterResponse register(RegisterRequest request, UUID tenantId) {
        Tenant tenant = tenantRepository.findByName(request.tenantName())
                .orElseGet(() -> tenantRepository.save(new Tenant(null, request.tenantName())));

        if (userRepository.findByEmailAndTenant_Name(request.email(), request.tenantName()).isPresent()) {
            throw new AuthException("User already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new AuthException("Role not found"));
        AccessLevel level = role.getAccessLevels().stream().findFirst()
                .orElseThrow(() -> new AuthException("Access level missing"));

        User user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setTenant(tenant);
        user.setRole(role);
        user.setAccessLevel(level);
        user = userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getEmail(), tenant.getId());
        return new RegisterResponse(token, mapper.toDto(user));
    }

    @Override
    public List<RoleDto> listRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).toList();
    }

    @Override
    public UserDto currentUser(String email, UUID tenantId) {
        User user = userRepository.findByEmailAndTenant_Id(email, tenantId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        return mapper.toDto(user);
    }
}
