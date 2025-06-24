package com.whatsbot.modules.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class FlowStepRequest {
    @NotNull
    private UUID contactId;
    private String intent;
    private Map<String, String> params;
}
