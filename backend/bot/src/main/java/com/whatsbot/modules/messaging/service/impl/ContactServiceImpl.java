package com.whatsbot.modules.messaging.service.impl;

import com.whatsbot.modules.messaging.dto.MessageHistoryDto;
import com.whatsbot.modules.messaging.dto.ContactDto;
import com.whatsbot.modules.messaging.repository.MessageRepository;
import com.whatsbot.modules.messaging.repository.ContactRepository;
import com.whatsbot.modules.messaging.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<ContactDto> findAll() {
        return contactRepository.findAll().stream()
                .map(u -> {
                    ContactDto dto = new ContactDto();
                    dto.setId(u.getId());
                    dto.setPhone(u.getPhone());
                    dto.setName(u.getName());
                    messageRepository.findFirstByContact_IdOrderByTimestampDesc(u.getId())
                            .ifPresent(m -> dto.setLastMessage(m.getText()));
                    return dto;
                })
                .toList();
    }

    @Override
    public List<MessageHistoryDto> getMessages(UUID contactId) {
        return messageRepository.findByContact_IdOrderByTimestampAsc(contactId).stream()
                .map(m -> {
                    MessageHistoryDto dto = new MessageHistoryDto();
                    dto.setId(m.getId());
                    dto.setContactId(contactId);
                    dto.setText(m.getText());
                    dto.setDirection(m.getDirection());
                    dto.setTimestamp(m.getTimestamp());
                    return dto;
                })
                .toList();
    }
}
