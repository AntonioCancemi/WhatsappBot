package com.whatsbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request payload for verifying tenant onboarding.
 */
@Data
public class OnboardVerifyRequest {
    @NotBlank
    private String tenantId;
}
