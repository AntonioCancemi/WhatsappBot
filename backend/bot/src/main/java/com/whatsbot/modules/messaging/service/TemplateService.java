package com.whatsbot.modules.messaging.service;

import java.util.UUID;

public interface TemplateService {
    void sendTemplate(UUID contactId, String templateName);
}
