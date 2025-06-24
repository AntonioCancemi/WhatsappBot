package com.whatsbot;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WhatsAppBotApplication {

    public static void main(String[] args) {
         SpringApplication.run(WhatsAppBotApplication.class, args);  
    }
}
