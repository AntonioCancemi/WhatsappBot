package com.whatsbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "twilio")
public class TwilioProperties {
    /** Auth token used to validate Twilio webhook signatures */
    private String authToken;

    /** Optional override for validation URL (e.g. Ngrok public URL) */
    private String webhookUrlOverride;
}
