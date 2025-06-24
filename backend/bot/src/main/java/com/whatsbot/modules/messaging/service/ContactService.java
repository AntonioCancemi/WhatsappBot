package com.whatsbot.modules.messaging.service;

import com.whatsbot.modules.messaging.dto.MessageHistoryDto;
import com.whatsbot.modules.messaging.dto.ContactDto;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    List<ContactDto> findAll();
    List<MessageHistoryDto> getMessages(UUID userId);
}
