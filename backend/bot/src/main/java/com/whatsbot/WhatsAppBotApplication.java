package com.whatsbot;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WhatsAppBotApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WhatsAppBotApplication.class);
        boolean hasProfile = Arrays.stream(args)
            .anyMatch(a -> a.startsWith("--spring.profiles.active"));
        if (!hasProfile) {
            app.setAdditionalProfiles("h2");
        }
        app.run(args);
    }
}
