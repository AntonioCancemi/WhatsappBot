package com.example.whatsapp.service;

import com.example.whatsapp.dto.TemplateMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WhatsAppSenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhatsAppSenderService.class);
    private final RestTemplate restTemplate;
    private final String token;
    private final String phoneNumberId;

    public WhatsAppSenderService(
            RestTemplate restTemplate,
            @Value("${whatsapp.access-token}") String token,
            @Value("${whatsapp.phone-number-id}") String phoneNumberId) {
        this.restTemplate = restTemplate;
        this.token = token;
        this.phoneNumberId = phoneNumberId;
    }

    public void sendTemplateMessage(String phoneNumber, String templateName, Map<String, String> parameters) {
        String url = String.format("https://graph.facebook.com/v17.0/%s/messages", phoneNumberId);
        List<String> values = parameters.values().stream().toList();
        TemplateMessageRequest request = new TemplateMessageRequest(phoneNumber, templateName, values);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TemplateMessageRequest> entity = new HttpEntity<>(request, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                LOGGER.info("WhatsApp message sent: {}", response.getBody());
            } else {
                LOGGER.error("Failed to send WhatsApp message: {} - {}", response.getStatusCode(), response.getBody());
            }
        } catch (Exception e) {
            LOGGER.error("Error sending WhatsApp message", e);
        }
    }
}
