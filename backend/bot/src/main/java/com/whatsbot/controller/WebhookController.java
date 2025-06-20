package com.whatsbot.controller;

import com.whatsbot.dto.webhook.WebhookRequestDto;
import com.whatsbot.dto.webhook.EntryDto;
import com.whatsbot.dto.webhook.ChangeDto;
import com.whatsbot.dto.webhook.ValueDto;
import com.whatsbot.dto.webhook.WhatsappMessageDto;
import com.whatsbot.service.MessageProcessorService;
import com.whatsbot.config.WebhookProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final MessageProcessorService messageProcessorService;
    private final WebhookProperties webhookProperties;

    @PostMapping("/receive")
    public ResponseEntity<Void> receive(
            @RequestHeader(value = "X-Webhook-Token", required = false) String token,
            @Valid @RequestBody WebhookRequestDto request) {
        if (token == null || !token.equals(webhookProperties.getToken())) {
            log.warn("Unauthorized webhook request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
