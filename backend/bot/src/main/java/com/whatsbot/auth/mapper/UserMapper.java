package com.whatsbot.auth.mapper;

import com.whatsbot.auth.dto.UserDto;
import com.whatsbot.auth.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().getName(),
                user.getTenant().getId().toString(),
                user.getAccessLevel().getCode()
        );
    }
}
