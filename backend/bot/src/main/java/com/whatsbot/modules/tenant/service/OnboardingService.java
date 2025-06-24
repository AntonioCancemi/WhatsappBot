package com.whatsbot.modules.tenant.service;

import com.whatsbot.modules.tenant.dto.OnboardStartRequest;
import com.whatsbot.modules.tenant.dto.OnboardStartResponse;
import com.whatsbot.modules.tenant.dto.OnboardVerifyRequest;
import com.whatsbot.modules.tenant.dto.OnboardVerifyResponse;

public interface OnboardingService {
    OnboardStartResponse startOnboarding(OnboardStartRequest request);

    OnboardVerifyResponse verifyPhone(OnboardVerifyRequest request);
}
