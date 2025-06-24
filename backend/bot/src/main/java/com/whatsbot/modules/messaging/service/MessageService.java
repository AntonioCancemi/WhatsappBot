package com.whatsbot.modules.messaging.service;

import com.whatsbot.modules.messaging.dto.MessageDto;
import java.util.List;

public interface MessageService {
    MessageDto save(MessageDto messageDto);
    List<MessageDto> findAll();
}
