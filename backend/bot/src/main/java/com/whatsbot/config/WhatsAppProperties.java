package com.whatsbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "whatsapp")
public class WhatsAppProperties {
    private String baseUrl;
    private String phoneNumberId;
    private String accessToken;
    /** App secret used to validate incoming webhook signatures */
    private String appSecret;
}
