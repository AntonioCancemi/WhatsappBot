package com.whatsbot.booking.service;

import com.whatsbot.booking.dto.FlowStepRequest;
import com.whatsbot.booking.dto.FlowStepResponse;

import java.util.List;

public interface ConversationalFlowService {
    List<String> listFlows();
    FlowStepResponse nextStep(String flowId, FlowStepRequest request);
}
