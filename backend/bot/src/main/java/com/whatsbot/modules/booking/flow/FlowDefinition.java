package com.whatsbot.modules.booking.flow;

import lombok.Data;

import java.util.List;

@Data
public class FlowDefinition {
    private String id;
    private List<FlowStep> steps;
}
