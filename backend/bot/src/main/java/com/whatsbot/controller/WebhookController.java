package com.whatsbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.config.WhatsAppProperties;
import com.whatsbot.dto.webhook.ChangeDto;
import com.whatsbot.dto.webhook.EntryDto;
import com.whatsbot.dto.webhook.ValueDto;
import com.whatsbot.dto.webhook.WebhookRequestDto;
import com.whatsbot.dto.webhook.WhatsappMessageDto;
import com.whatsbot.service.MessageProcessorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.util.HexFormat;
import java.util.Set;

/**
 * Receives webhook events from WhatsApp Cloud API.
 */
@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final MessageProcessorService messageProcessorService;
    private final WhatsAppProperties whatsAppProperties;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @PostMapping(path = "/receive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> receiveWebhook(@RequestBody byte[] body, HttpServletRequest request) {
        String signature = request.getHeader("X-Hub-Signature");
        if (!isValidSignature(body, signature)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        WebhookRequestDto payload;
        try {
            payload = objectMapper.readValue(body, WebhookRequestDto.class);
        } catch (Exception e) {
            log.error("Invalid webhook payload", e);
            return ResponseEntity.badRequest().build();
        }

        Set<ConstraintViolation<WebhookRequestDto>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        for (EntryDto entry : payload.getEntry()) {
            for (ChangeDto change : entry.getChanges()) {
                ValueDto value = change.getValue();
                if (value.getMessages() != null) {
                    for (WhatsappMessageDto msg : value.getMessages()) {
                        String text = msg.getText() != null ? msg.getText().getBody() : "";
                        messageProcessorService.processIncomingMessage(msg.getFrom(), text, null);
                    }
                }
            }
        }
        return ResponseEntity.ok().build();
    }

    private boolean isValidSignature(byte[] payload, String header) {
        if (!StringUtils.hasText(header) || !header.startsWith("sha256=")) {
            return false;
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(whatsAppProperties.getAppSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(key);
            byte[] expected = mac.doFinal(payload);
            String expectedHeader = "sha256=" + HexFormat.of().formatHex(expected);
            return MessageDigest.isEqual(expectedHeader.getBytes(StandardCharsets.UTF_8), header.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("Failed to verify signature", e);
            return false;
        }
    }
}
