package com.whatsbot.controller;

import com.whatsbot.interceptor.ValidateTwilioSignature;
import com.whatsbot.service.MessageProcessorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final MessageProcessorService messageProcessorService;

    @PostMapping(
            path     = "/receive",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ValidateTwilioSignature
    public ResponseEntity<String> receiveWebhook(
            @RequestParam MultiValueMap<String, String> formParams
    ) {
        Map<String,String> params = formParams.toSingleValueMap();

        String from = params.get("From");
        String body = params.get("Body");
        String sid  = params.get("MessageSid");
        log.info("Received Twilio message SID={} from={} body=\"{}\"", sid, from, body);
        messageProcessorService.processIncomingMessage(from, body, sid);

        return ResponseEntity.ok("{}");
    }
}
