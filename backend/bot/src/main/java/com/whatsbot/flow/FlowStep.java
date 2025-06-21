package com.whatsbot.flow;

import lombok.Data;

import java.util.Map;

@Data
public class FlowStep {
    private String id;
    private String message;
    private Map<String, String> transitions;
}
