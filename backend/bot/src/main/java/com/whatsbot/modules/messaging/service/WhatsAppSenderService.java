package com.whatsbot.modules.messaging.service;

import com.whatsbot.modules.messaging.dto.TemplateMessageRequest;
import com.whatsbot.core.config.WhatsAppProperties;
import com.whatsbot.core.service.MessageAuditService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WhatsAppSenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhatsAppSenderService.class);
    private final RestTemplate restTemplate;
    private final WhatsAppProperties properties;
    private final MessageAuditService messageAuditService;

    public void sendTemplateMessage(UUID messageId, String phoneNumber, String templateName, String intent, Map<String, String> parameters) {
        String url = String.format("%s/%s/messages", properties.getBaseUrl(), properties.getPhoneNumberId());
 
        List<String> values = parameters.values().stream().toList();
        TemplateMessageRequest request = new TemplateMessageRequest(phoneNumber, templateName, values);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(properties.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TemplateMessageRequest> entity = new HttpEntity<>(request, headers);
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            success = response.getStatusCode().is2xxSuccessful();
            if (success) {
 
                LOGGER.info("WhatsApp message sent: {}", response.getBody());
            } else {
                LOGGER.error("Failed to send WhatsApp message: {} - {}", response.getStatusCode(), response.getBody());
            }
        } catch (Exception e) {
            LOGGER.error("Error sending WhatsApp message", e);
        } finally {
            long responseTime = System.currentTimeMillis() - start;
            messageAuditService.log(messageId, intent, responseTime, success);
        }
    }

    public void sendTextMessage(UUID messageId, String phoneNumber, String text, String intent) {
        String url = String.format("%s/%s/messages", properties.getBaseUrl(), properties.getPhoneNumberId());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(properties.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", phoneNumber,
                "type", "text",
                "text", Map.of("body", text)
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            success = response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            LOGGER.error("Error sending text message", e);
        } finally {
            long responseTime = System.currentTimeMillis() - start;
            messageAuditService.log(messageId, intent, responseTime, success);
        }
    }
}
