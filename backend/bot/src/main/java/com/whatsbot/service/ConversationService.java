package com.whatsbot.service;

import com.whatsbot.dto.FlowStepResponse;

import java.util.UUID;

public interface ConversationService {
    FlowStepResponse startBooking(UUID userId);
    FlowStepResponse next(UUID stateId, String userResponse);
}
