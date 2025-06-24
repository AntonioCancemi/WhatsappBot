package com.whatsbot.broadcast.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class BroadcastCreateRequest {
    @NotBlank
    private String text;

    @NotNull
    private Instant scheduledAt;
}
