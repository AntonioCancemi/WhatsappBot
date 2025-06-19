package com.whatsbot.service;

import java.util.UUID;

public interface MessageAuditService {
    void log(UUID messageId, String intent, long responseTimeMs, boolean success);
}
