package com.whatsbot.service;

import java.util.UUID;

public interface TemplateService {
    void sendTemplate(UUID userId, String templateName);
}
