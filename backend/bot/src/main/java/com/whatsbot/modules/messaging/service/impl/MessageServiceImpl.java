package com.whatsbot.modules.messaging.service.impl;

import com.whatsbot.modules.messaging.dto.MessageDto;
import com.whatsbot.modules.messaging.model.Message;
import com.whatsbot.modules.messaging.model.MessageDirection;
import com.whatsbot.modules.messaging.repository.MessageRepository;
import com.whatsbot.modules.messaging.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final MessageRepository messageRepository;

    @Override
    public MessageDto save(MessageDto messageDto) {
        Message message = new Message();
        message.setText(messageDto.getText());
        message.setIntent(messageDto.getIntent());
        message.setDirection(MessageDirection.IN);
        Message saved = messageRepository.save(message);
        log.info("Saved message with id {}", saved.getId());
        messageDto.setId(saved.getId());
        return messageDto;
    }

    @Override
    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream()
                .map(m -> {
                    MessageDto dto = new MessageDto();
                    dto.setId(m.getId());
                    dto.setText(m.getText());
                    dto.setIntent(m.getIntent());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
