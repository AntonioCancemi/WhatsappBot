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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OnboardingServiceImpl implements OnboardingService {

    private final TenantConfigRepository tenantConfigRepository;

    @Override
    public OnboardStartResponse startOnboarding(OnboardStartRequest request) {
        TenantConfig config = new TenantConfig();
        config.setBusinessName(request.getBusinessName());
        config.setPhoneNumber(request.getPhoneNumber());
        config.setAccessToken(request.getAccessToken());
        config.setAppSecret(request.getAppSecret());
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
