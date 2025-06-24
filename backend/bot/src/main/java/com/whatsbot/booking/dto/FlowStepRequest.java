package com.whatsbot.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class FlowStepRequest {
    @NotNull
    private UUID userId;
    private String intent;
    private Map<String, String> params;
}
