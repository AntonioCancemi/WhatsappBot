package com.whatsbot.modules.auth.mapper;

import com.whatsbot.modules.auth.dto.RoleDto;
import com.whatsbot.modules.auth.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }
        return new RoleDto(role.getId(), role.getName());
    }
}
