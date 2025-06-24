package com.whatsbot.user.dto;

import com.whatsbot.user.model.MessageDirection;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class MessageHistoryDto {
    private UUID id;
    private UUID userId;
    private String text;
    private MessageDirection direction;
    private Instant timestamp;
}
