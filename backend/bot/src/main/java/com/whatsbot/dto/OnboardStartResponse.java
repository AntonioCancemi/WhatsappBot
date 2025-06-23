package com.whatsbot.dto;

import lombok.Data;

import java.util.UUID;

/**
 * Response returned after starting the onboarding flow.
 */
@Data
public class OnboardStartResponse {
    private UUID tenantId;
    private String token;
}
