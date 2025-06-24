package com.whatsbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request payload for starting tenant onboarding.
 */
@Data
public class OnboardStartRequest {
    @NotBlank
    private String businessName;
    @NotBlank
    private String phoneNumber;
    // TODO - Uncomment and implement validation for access token and app secret
    // These fields are required for WhatsApp Cloud API integration.
    // @NotBlank
    // private String accessToken;
    // @NotBlank
    // private String appSecret;
}
