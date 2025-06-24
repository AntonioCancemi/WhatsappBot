package com.whatsbot.auth.mapper;

import com.whatsbot.auth.dto.UserDto;
import com.whatsbot.auth.model.AuthUser;
import org.springframework.stereotype.Component;

/**
 * Simple mapper for converting {@link AuthUser} entities to {@link UserDto}
 * without relying on MapStruct.
 */
@Component
public class AuthUserMapper {

    public UserDto toDto(AuthUser user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getTenant() != null ? user.getTenant().getId() : null,
                user.getRoles().stream().map(r -> r.getName()).toList());
    }

}