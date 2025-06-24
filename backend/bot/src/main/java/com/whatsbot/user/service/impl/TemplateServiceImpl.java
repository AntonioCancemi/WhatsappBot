package com.whatsbot.user.service.impl;

import com.whatsbot.user.model.Message;
import com.whatsbot.user.model.MessageDirection;
import com.whatsbot.user.model.User;
import com.whatsbot.user.repository.MessageRepository;
import com.whatsbot.user.repository.UserRepository;
import com.whatsbot.user.service.TemplateService;
import com.whatsbot.user.service.WhatsAppSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final WhatsAppSenderService whatsAppSenderService;

    @Override
    public void sendTemplate(UUID userId, String templateName) {
        User user = userRepository.findById(userId)
                .orElseThrow();
        Message message = new Message();
        message.setUser(user);
        message.setText(templateName);
        message.setDirection(MessageDirection.OUT);
        message.setIntent("TEMPLATE");
        Message saved = messageRepository.save(message);

        whatsAppSenderService.sendTemplateMessage(saved.getId(), user.getPhone(), templateName, "TEMPLATE", Map.of());
    }
}
