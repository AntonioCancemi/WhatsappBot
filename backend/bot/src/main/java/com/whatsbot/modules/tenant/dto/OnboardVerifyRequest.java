package com.whatsbot.modules.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

/**
 * Request payload for verifying tenant onboarding.
 */
@Data
public class OnboardVerifyRequest {
    private UUID tenantId;

    @NotBlank
    private String smsCode;
}
