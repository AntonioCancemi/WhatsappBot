package com.whatsbot.modules.tenant.dto;

import lombok.Data;

/**
 * Response returned after verifying tenant onboarding.
 */
@Data
public class OnboardVerifyResponse {
    private boolean success;
}
