package com.whatsbot.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "webhook")
public class WebhookProperties {
    /** Token used to authorize incoming webhook requests */
    private String token;

}
