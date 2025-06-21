package com.whatsbot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "info")
@Data
public class InfoProperties {
    private Map<String, String> openHours;
}
