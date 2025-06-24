package com.whatsbot.auth.dto;

import java.util.List;
import java.util.UUID;

public record UserDto(UUID id, String username, UUID tenantId, List<String> roles) {
}
