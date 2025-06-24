package com.whatsbot.service.impl;

import com.whatsbot.dto.OnboardStartRequest;
import com.whatsbot.dto.OnboardStartResponse;
import com.whatsbot.dto.OnboardVerifyRequest;
import com.whatsbot.dto.OnboardVerifyResponse;
import com.whatsbot.model.TenantConfig;
import com.whatsbot.repository.TenantConfigRepository;
import com.whatsbot.service.OnboardingService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final TenantConfigRepository tenantConfigRepository;

    @Override
    public OnboardStartResponse startOnboarding(OnboardStartRequest request) {
        TenantConfig config = new TenantConfig();
        config.setBusinessName(request.getBusinessName());
        config.setPhoneNumber(request.getPhoneNumber());
        // config.setAccessToken(request.getAccessToken());
        // config.setAppSecret(request.getAppSecret());
        config.setAccessToken("fnwfoewfq2j41v39lvutc76fvibobgnn8"); // Placeholder for access token
        config.setAppSecret("dnaifan8wafnwffo23fe43futicutitixxu"); // Placeholder for app secret
        TenantConfig saved = tenantConfigRepository.save(config);

        OnboardStartResponse response = new OnboardStartResponse();
        response.setTenantId(saved.getId());
        response.setToken(generateJwt(saved.getId(), saved.getAppSecret()));
        return response;
    }

    @Override
    public OnboardVerifyResponse verifyPhone(OnboardVerifyRequest request) {
        OnboardVerifyResponse response = new OnboardVerifyResponse();
        response.setSuccess(true);
        return response;
    }

    private String generateJwt(UUID tenantId, String secret) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
            .setSubject(tenantId.toString())
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}
