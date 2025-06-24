package com.whatsbot.user.service.impl;

import com.whatsbot.user.dto.MessageDto;
import com.whatsbot.intent.IntentType;
import com.whatsbot.user.repository.UserRepository;
import com.whatsbot.user.service.BookingService;
import com.whatsbot.user.service.HelpService;
import com.whatsbot.user.service.InfoService;
import com.whatsbot.user.service.IntentClassifierService;
import com.whatsbot.common.service.MessageAuditService;
import com.whatsbot.user.service.MessageProcessorService;
import com.whatsbot.user.service.MessageService;
import com.whatsbot.user.service.WhatsAppSenderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProcessorServiceImpl implements MessageProcessorService {

    private static final Logger log = LoggerFactory.getLogger(MessageProcessorServiceImpl.class);

    private final MessageService messageService;
    private final IntentClassifierService intentClassifierService;
    private final MessageAuditService messageAuditService;
    private final InfoService infoService;
    private final HelpService helpService;
    private final BookingService bookingService;
    private final WhatsAppSenderService whatsAppSenderService;
    private final UserRepository userRepository;

    @Override
    public void processIncomingMessage(String sender, String message, String sid) {
        long start = System.currentTimeMillis();
        IntentType intent = intentClassifierService.classify(message);

        MessageDto dto = new MessageDto();
        dto.setText(message);
        dto.setIntent(intent.name());
        MessageDto saved = messageService.save(dto);

        switch (intent) {
            case INFO -> {
                String text = String.join(", ", infoService.getOpenHours().values());
                whatsAppSenderService.sendTextMessage(saved.getId(), sender, text, intent.name());
            }
            case CANCEL -> userRepository.findByPhone(sender)
                    .ifPresent(u -> bookingService.cancelLatestBookingForUser(u.getId()));
            case GENERIC -> {
                if (message.toLowerCase().contains("help")) {
                    String helpText = String.join(", ", helpService.getHelp().keySet());
                    whatsAppSenderService.sendTextMessage(saved.getId(), sender, helpText, "HELP");
                }
            }
            default -> {
            }
        }

        long responseTime = System.currentTimeMillis() - start;
        messageAuditService.log(saved.getId(), intent.name(), responseTime, true);
        log.info("Processed message from {} with saved id {}", sender, saved.getId());
    }
}
