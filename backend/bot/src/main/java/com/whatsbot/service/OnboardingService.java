package com.whatsbot.service;

import com.whatsbot.dto.OnboardStartRequest;
import com.whatsbot.dto.OnboardStartResponse;
import com.whatsbot.dto.OnboardVerifyRequest;
import com.whatsbot.dto.OnboardVerifyResponse;

public interface OnboardingService {
    OnboardStartResponse startOnboarding(OnboardStartRequest request);

    OnboardVerifyResponse verifyPhone(OnboardVerifyRequest request);
}
