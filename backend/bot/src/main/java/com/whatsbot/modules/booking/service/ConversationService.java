package com.whatsbot.modules.booking.service;

import com.whatsbot.modules.booking.dto.FlowStepResponse;

import java.util.UUID;

public interface ConversationService {
    FlowStepResponse startBooking(UUID contactId);
    FlowStepResponse next(UUID stateId, String userResponse);
}
