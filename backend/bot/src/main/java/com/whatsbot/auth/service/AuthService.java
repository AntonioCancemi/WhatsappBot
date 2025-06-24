package com.whatsbot.auth.service;

import com.whatsbot.auth.dto.*;

import java.util.List;
import java.util.UUID;

public interface AuthService {
    LoginResponse login(LoginRequest request, UUID tenantId);

    RegisterResponse register(RegisterRequest request, UUID tenantId);

    UserDto currentUser(String email, UUID tenantId);

    List<RoleDto> listRoles();
}
