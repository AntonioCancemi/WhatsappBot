package com.whatsbot.modules.auth.dto;

import java.util.UUID;

public record UserDto(UUID id, String email, String fullName, String role, String tenantId, String accessLevel) {}
