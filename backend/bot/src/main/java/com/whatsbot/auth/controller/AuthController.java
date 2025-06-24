package com.whatsbot.auth.controller;

import com.whatsbot.auth.dto.LoginRequest;
import com.whatsbot.auth.dto.LoginResponse;
import com.whatsbot.auth.dto.RegisterRequest;
import com.whatsbot.auth.dto.RoleDto;
import com.whatsbot.auth.dto.UserDto;
import com.whatsbot.auth.service.AuthService;
import com.whatsbot.auth.middleware.TenantContext;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        UUID tenantId = TenantContext.get();
        return authService.currentUser(authentication.getName(), tenantId);
    }

    @GetMapping("/roles")
    public java.util.List<RoleDto> roles() {
        return authService.listRoles();
    }
}
