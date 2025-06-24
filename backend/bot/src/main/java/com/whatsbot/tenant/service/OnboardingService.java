package com.whatsbot.tenant.service;

import com.whatsbot.tenant.dto.OnboardStartRequest;
import com.whatsbot.tenant.dto.OnboardStartResponse;
import com.whatsbot.tenant.dto.OnboardVerifyRequest;
import com.whatsbot.tenant.dto.OnboardVerifyResponse;

public interface OnboardingService {
    OnboardStartResponse startOnboarding(OnboardStartRequest request);

    OnboardVerifyResponse verifyPhone(OnboardVerifyRequest request);
}
