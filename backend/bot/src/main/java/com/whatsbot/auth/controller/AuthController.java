package com.whatsbot.auth.controller;

import com.whatsbot.auth.dto.LoginRequest;
import com.whatsbot.auth.dto.LoginResponse;
import com.whatsbot.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
