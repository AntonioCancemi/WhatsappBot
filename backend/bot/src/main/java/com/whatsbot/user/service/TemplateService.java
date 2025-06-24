package com.whatsbot.user.service;

import java.util.UUID;

public interface TemplateService {
    void sendTemplate(UUID userId, String templateName);
}
