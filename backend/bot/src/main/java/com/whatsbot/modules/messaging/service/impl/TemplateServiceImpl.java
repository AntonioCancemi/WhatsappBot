package com.whatsbot.modules.messaging.service.impl;

import com.whatsbot.modules.messaging.model.Message;
import com.whatsbot.modules.messaging.model.MessageDirection;
import com.whatsbot.modules.messaging.model.Contact;
import com.whatsbot.modules.messaging.repository.MessageRepository;
import com.whatsbot.modules.messaging.repository.ContactRepository;
import com.whatsbot.modules.messaging.service.TemplateService;
import com.whatsbot.modules.messaging.service.WhatsAppSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final ContactRepository contactRepository;
    private final MessageRepository messageRepository;
    private final WhatsAppSenderService whatsAppSenderService;

    @Override
    public void sendTemplate(UUID contactId, String templateName) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow();
        Message message = new Message();
        message.setContact(contact);
        message.setText(templateName);
        message.setDirection(MessageDirection.OUT);
        message.setIntent("TEMPLATE");
        Message saved = messageRepository.save(message);

        whatsAppSenderService.sendTemplateMessage(saved.getId(), contact.getPhone(), templateName, "TEMPLATE", Map.of());
    }
}
