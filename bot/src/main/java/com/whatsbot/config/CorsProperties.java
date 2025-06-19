package com.whatsbot.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private boolean enabled = false;
    private List<String> allowedOrigins = List.of("*");
}
