package com.whatsbot.controller;

import com.whatsbot.dto.webhook.WebhookRequestDto;
import com.whatsbot.dto.webhook.EntryDto;
import com.whatsbot.dto.webhook.ChangeDto;
import com.whatsbot.dto.webhook.ValueDto;
import com.whatsbot.dto.webhook.WhatsappMessageDto;
import com.whatsbot.service.MessageProcessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final MessageProcessorService messageProcessorService;

    @PostMapping("/receive")
    public ResponseEntity<Void> receive(@Valid @RequestBody WebhookRequestDto request) {
        if (request.getEntry() != null) {
            for (EntryDto entry : request.getEntry()) {
                if (entry.getChanges() != null) {
                    for (ChangeDto change : entry.getChanges()) {
                        ValueDto value = change.getValue();
                        if (value != null && value.getMessages() != null) {
                            for (WhatsappMessageDto msg : value.getMessages()) {
                                String sender = msg.getFrom();
                                String body = msg.getText() != null ? msg.getText().getBody() : null;
                                log.info("Received message from {}: {}", sender, body);
                                messageProcessorService.processIncomingMessage(sender, body);
                            }
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok().build();
    }
}
