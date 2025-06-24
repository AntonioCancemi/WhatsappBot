package com.whatsbot.modules.messaging.dto;

import com.whatsbot.modules.messaging.model.MessageDirection;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class MessageHistoryDto {
    private UUID id;
    private UUID contactId;
    private String text;
    private MessageDirection direction;
    private Instant timestamp;
}
