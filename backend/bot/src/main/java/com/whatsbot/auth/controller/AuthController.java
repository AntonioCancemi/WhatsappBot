package com.whatsbot.auth.controller;

import com.whatsbot.auth.dto.*;
 
import com.whatsbot.auth.service.AuthService;
import com.whatsbot.auth.middleware.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UUID tenantId = TenantContext.get();
        return authService.login(request, tenantId);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        UUID tenantId = TenantContext.get();
        return authService.register(request, tenantId);
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
    public List<RoleDto> roles() {
    public java.util.List<RoleDto> roles() {
        return authService.listRoles();
    }
}
