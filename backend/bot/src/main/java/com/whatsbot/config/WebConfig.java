package com.whatsbot.config;

import com.whatsbot.interceptor.TwilioValidationHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;
    private final TwilioValidationHandlerInterceptor twilioValidationHandlerInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (corsProperties.isEnabled()) {
            registry.addMapping("/**")
                    .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]))
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(twilioValidationHandlerInterceptor);
    }
}
