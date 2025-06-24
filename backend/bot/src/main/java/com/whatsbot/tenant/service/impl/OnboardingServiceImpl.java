package com.whatsbot.tenant.service.impl;

import com.whatsbot.tenant.dto.OnboardStartRequest;
import com.whatsbot.tenant.dto.OnboardStartResponse;
import com.whatsbot.tenant.dto.OnboardVerifyRequest;
import com.whatsbot.tenant.dto.OnboardVerifyResponse;
import com.whatsbot.tenant.model.TenantConfig;
import com.whatsbot.tenant.repository.TenantConfigRepository;
import com.whatsbot.tenant.service.OnboardingService;
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
        response.setToken(createJwt(saved.getId(), saved.getAppSecret()));
        return response;
    }

    @Override
    public OnboardVerifyResponse verifyPhone(OnboardVerifyRequest request) {
        return tenantConfigRepository.findById(request.getTenantId())
            .map(cfg -> {
                cfg.setPhoneNumberId("verified" + request.getSmsCode());
                tenantConfigRepository.save(cfg);
                OnboardVerifyResponse response = new OnboardVerifyResponse();
                response.setSuccess(true);
                return response;
            })
            .orElseGet(() -> {
                OnboardVerifyResponse response = new OnboardVerifyResponse();
                response.setSuccess(false);
                return response;
            });
    }

    private String createJwt(UUID tenantId, String secret) {
        String header = Base64.getUrlEncoder().withoutPadding()
            .encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
        String payload = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(("{\"tenantId\":\"" + tenantId + "\"}").getBytes(StandardCharsets.UTF_8));
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] sig = mac.doFinal((header + "." + payload).getBytes(StandardCharsets.UTF_8));
            String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(sig);
            return header + "." + payload + "." + signature;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("Cannot generate JWT", e);
        }
    }
}
