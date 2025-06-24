package com.whatsbot.broadcast.dto;

import com.whatsbot.broadcast.model.BroadcastStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class BroadcastMessageDto {
    private UUID id;
    private String text;
    private Instant scheduledAt;
    private BroadcastStatus status;
}
