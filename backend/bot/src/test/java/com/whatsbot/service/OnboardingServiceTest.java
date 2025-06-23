package com.whatsbot.service;

import com.whatsbot.dto.OnboardStartRequest;
import com.whatsbot.dto.OnboardVerifyRequest;
import com.whatsbot.model.TenantConfig;
import com.whatsbot.repository.TenantConfigRepository;
import com.whatsbot.service.impl.OnboardingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class OnboardingServiceTest {

    @Mock
    private TenantConfigRepository tenantConfigRepository;

    @InjectMocks
    private OnboardingServiceImpl onboardingService;

    private OnboardStartRequest startRequest;

    @BeforeEach
    void setup() {
        startRequest = new OnboardStartRequest();
        startRequest.setBaseUrl("http://test");
        startRequest.setPhoneNumberId("123");
        startRequest.setAccessToken("token");
        startRequest.setAppSecret("secret");
    }

    @Test
    void startCreatesTenant() {
        when(tenantConfigRepository.save(any(TenantConfig.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = onboardingService.start(startRequest);

        ArgumentCaptor<TenantConfig> captor = ArgumentCaptor.forClass(TenantConfig.class);
        verify(tenantConfigRepository).save(captor.capture());
        assertThat(captor.getValue().getTenantId()).isNotBlank();
        assertThat(response.getTenantId()).isEqualTo(captor.getValue().getTenantId());
    }

    @Test
    void verifyExistingTenant() {
        when(tenantConfigRepository.findByTenantId("abc"))
                .thenReturn(Optional.of(new TenantConfig()));
        OnboardVerifyRequest request = new OnboardVerifyRequest();
        request.setTenantId("abc");

        var response = onboardingService.verify(request);

        assertThat(response.isVerified()).isTrue();
    }

    @Test
    void verifyNonExistingTenant() {
        when(tenantConfigRepository.findByTenantId("missing"))
                .thenReturn(Optional.empty());
        OnboardVerifyRequest request = new OnboardVerifyRequest();
        request.setTenantId("missing");

        var response = onboardingService.verify(request);

        assertThat(response.isVerified()).isFalse();
    }
}
