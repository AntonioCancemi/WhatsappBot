package com.whatsbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Request payload for starting tenant onboarding.
 */
@Data
public class OnboardStartRequest {
    @NotBlank
    private String baseUrl;
    @NotBlank
    private String phoneNumberId;
    @NotBlank
    private String accessToken;
    @NotBlank
    private String appSecret;
}
