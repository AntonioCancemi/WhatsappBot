package com.whatsbot.service;

import com.whatsbot.dto.MessageHistoryDto;
import com.whatsbot.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAll();
    List<MessageHistoryDto> getMessages(UUID userId);
}
