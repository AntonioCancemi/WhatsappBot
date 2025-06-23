package com.whatsbot.dto;

import lombok.Data;

/**
 * Response returned after starting the onboarding flow.
 */
@Data
public class OnboardStartResponse {
    private String tenantId;
}
