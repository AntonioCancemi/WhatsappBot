package com.whatsbot.dto;

import lombok.Data;

@Data
public class FlowStepResponse {
    private String step;
    private String message;
    private java.util.UUID stateId;
}
