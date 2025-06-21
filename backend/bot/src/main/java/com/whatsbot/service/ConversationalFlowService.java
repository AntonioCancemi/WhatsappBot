package com.whatsbot.service;

import com.whatsbot.dto.FlowStepRequest;
import com.whatsbot.dto.FlowStepResponse;

import java.util.List;

public interface ConversationalFlowService {
    List<String> listFlows();
    FlowStepResponse nextStep(String flowId, FlowStepRequest request);
}
