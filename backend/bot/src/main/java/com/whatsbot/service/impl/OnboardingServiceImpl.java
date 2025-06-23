package com.whatsbot.service.impl;

import com.whatsbot.dto.OnboardStartRequest;
import com.whatsbot.dto.OnboardStartResponse;
import com.whatsbot.dto.OnboardVerifyRequest;
import com.whatsbot.dto.OnboardVerifyResponse;
import com.whatsbot.model.TenantConfig;
import com.whatsbot.repository.TenantConfigRepository;
import com.whatsbot.service.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final TenantConfigRepository tenantConfigRepository;

    @Override
    public OnboardStartResponse start(OnboardStartRequest request) {
        TenantConfig config = new TenantConfig();
        config.setTenantId(UUID.randomUUID().toString());
        config.setBaseUrl(request.getBaseUrl());
        config.setPhoneNumberId(request.getPhoneNumberId());
        config.setAccessToken(request.getAccessToken());
        config.setAppSecret(request.getAppSecret());
        TenantConfig saved = tenantConfigRepository.save(config);

        OnboardStartResponse response = new OnboardStartResponse();
        response.setTenantId(saved.getTenantId());
        return response;
    }

    @Override
    public OnboardVerifyResponse verify(OnboardVerifyRequest request) {
        boolean exists = tenantConfigRepository.findByTenantId(request.getTenantId()).isPresent();
        OnboardVerifyResponse response = new OnboardVerifyResponse();
        response.setVerified(exists);
        return response;
    }
}
