package com.whatsbot.common.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private boolean enabled = false;
    /**
     * Explicitly allowed origins for CORS. Empty by default to avoid
     * permissive configuration.
     */
    private List<String> allowedOrigins = List.of();
}
