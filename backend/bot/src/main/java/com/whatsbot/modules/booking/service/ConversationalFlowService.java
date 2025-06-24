package com.whatsbot.modules.booking.service;

import com.whatsbot.modules.booking.dto.FlowStepRequest;
import com.whatsbot.modules.booking.dto.FlowStepResponse;

import java.util.List;

public interface ConversationalFlowService {
    List<String> listFlows();
    FlowStepResponse nextStep(String flowId, FlowStepRequest request);
}
