package com.whatsbot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.service.MessageAuditService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageAuditServiceImpl implements MessageAuditService {

    private static final Logger log = LoggerFactory.getLogger(MessageAuditServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void log(UUID messageId, String intent, long responseTimeMs, boolean success) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("messageId", messageId);
        payload.put("intent", intent);
        payload.put("responseTimeMs", responseTimeMs);
        payload.put("sendOutcome", success ? "SUCCESS" : "FAILURE");
        try {
            log.info(objectMapper.writeValueAsString(payload));
        } catch (JsonProcessingException e) {
            log.error("Unable to write audit log", e);
        }
    }
}
