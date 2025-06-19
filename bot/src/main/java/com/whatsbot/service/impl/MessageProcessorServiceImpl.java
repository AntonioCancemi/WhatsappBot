package com.whatsbot.service.impl;

import com.whatsbot.dto.MessageDto;
import com.whatsbot.service.MessageProcessorService;
import com.whatsbot.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProcessorServiceImpl implements MessageProcessorService {

    private static final Logger log = LoggerFactory.getLogger(MessageProcessorServiceImpl.class);

    private final MessageService messageService;

    @Override
    public void processIncomingMessage(String sender, String message) {
        MessageDto dto = new MessageDto();
        dto.setText(message);
        MessageDto saved = messageService.save(dto);
        log.info("Processed message from {} with saved id {}", sender, saved.getId());
    }
}
