package com.whatsbot.controller;

import com.twilio.security.RequestValidator;
import com.whatsbot.config.WebhookProperties;
import com.whatsbot.service.MessageProcessorService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final Logger log = LoggerFactory.getLogger(WebhookController.class);

    private final MessageProcessorService messageProcessorService;
    private final WebhookProperties webhookProperties;

    @PostMapping(
            path     = "/receive",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> receiveWebhook(
            HttpServletRequest request,
            @RequestHeader("X-Twilio-Signature") String signature,
            @RequestParam MultiValueMap<String, String> formParams
    ) {
        // 1) Otteniamo il base URL pubblico dal header X-Forwarded-Proto + Host
        String proto = request.getHeader("X-Forwarded-Proto");
        if (proto == null) {
            proto = request.getScheme(); // fallback
        }
        String host = request.getHeader("Host");
        String url  = proto + "://" + host + request.getRequestURI();

        // 2) Validator con token
        RequestValidator validator = new RequestValidator(webhookProperties.getTwilioAuthToken());

        // 3) Converto param in Map<String,String>
        Map<String,String> params = formParams.toSingleValueMap();

        // 4) Verifico signature
        if (!validator.validate(url, params, signature)) {
            log.warn("Invalid Twilio signature for URL {} with params {}", url, params.keySet());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("{\"error\":\"Invalid signature\"}");
        }

        // 5) Estraggo e processo
        String from = params.get("From");
        String body = params.get("Body");
        String sid  = params.get("MessageSid");
        log.info("Received Twilio message SID={} from={} body=\"{}\"", sid, from, body);
        messageProcessorService.processIncomingMessage(from, body, sid);

        return ResponseEntity.ok("{}");
    }
}
