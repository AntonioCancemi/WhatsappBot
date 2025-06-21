package com.whatsbot.controller;

import com.twilio.security.RequestValidator;
import com.whatsbot.config.WebhookProperties;
import com.whatsbot.service.MessageProcessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final MessageProcessorService messageProcessorService;
    private final WebhookProperties webhookProperties;

    @PostMapping(
            path = "/receive",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> receiveWebhook(
            @RequestHeader("X-Twilio-Signature") String signature,
            @RequestParam MultiValueMap<String, String> formParams
    ) {
        String url = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        RequestValidator validator = new RequestValidator(webhookProperties.getTwilioAuthToken());
        if (!validator.validate(url, formParams.toSingleValueMap(), signature)) {
            log.warn("Invalid Twilio signature");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}");
        }

        String from = formParams.getFirst("From");
        String body = formParams.getFirst("Body");
        String sid = formParams.getFirst("MessageSid");
        log.info("Received Twilio message {} from {}", sid, from);
        messageProcessorService.processIncomingMessage(from, body);
        return ResponseEntity.ok("{}");
    }
}
