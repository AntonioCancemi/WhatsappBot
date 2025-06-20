package com.whatsbot.service;

import com.whatsbot.dto.MessageDto;
import java.util.List;

public interface MessageService {
    MessageDto save(MessageDto messageDto);
    List<MessageDto> findAll();
}
