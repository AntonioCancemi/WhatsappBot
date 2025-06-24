package com.whatsbot.booking.service;

import com.whatsbot.booking.dto.FlowStepResponse;

import java.util.UUID;

public interface ConversationService {
    FlowStepResponse startBooking(UUID userId);
    FlowStepResponse next(UUID stateId, String userResponse);
}
