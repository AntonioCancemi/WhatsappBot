package com.whatsbot.user.service;

import com.whatsbot.user.dto.MessageDto;
import java.util.List;

public interface MessageService {
    MessageDto save(MessageDto messageDto);
    List<MessageDto> findAll();
}
