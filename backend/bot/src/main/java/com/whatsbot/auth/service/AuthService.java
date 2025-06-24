package com.whatsbot.auth.service;

import com.whatsbot.auth.dto.LoginRequest;
import com.whatsbot.auth.dto.LoginResponse;
import com.whatsbot.auth.dto.UserDto;
import com.whatsbot.auth.exception.AuthException;
import com.whatsbot.auth.exception.UnauthorizedException;
import com.whatsbot.auth.mapper.AuthUserMapper;
import com.whatsbot.auth.model.AuthUser;
import com.whatsbot.auth.repository.AuthUserRepository;
import com.whatsbot.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthUserMapper mapper;

    public LoginResponse login(LoginRequest request) {
        AuthUser user = userRepository.findByUsernameAndTenant_Name(request.username(), request.tenant())
                .orElseThrow(() -> new AuthException("Invalid credentials"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getTenant().getId());
        return new LoginResponse(token);
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
