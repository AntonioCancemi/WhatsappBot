package com.whatsbot.user.service;

import com.whatsbot.user.dto.MessageHistoryDto;
import com.whatsbot.user.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAll();
    List<MessageHistoryDto> getMessages(UUID userId);
}
