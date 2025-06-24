// package com.whatsbot.service;

// import com.whatsbot.dto.OnboardStartRequest;
// import com.whatsbot.dto.OnboardVerifyRequest;
// import com.whatsbot.model.TenantConfig;
// import com.whatsbot.repository.TenantConfigRepository;
// import com.whatsbot.service.impl.OnboardingServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.UUID;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import org.junit.jupiter.api.extension.ExtendWith;

// @ExtendWith(MockitoExtension.class)
// class OnboardingServiceTest {

//     @Mock
//     private TenantConfigRepository tenantConfigRepository;

//     @InjectMocks
//     private OnboardingServiceImpl onboardingService;

//     private OnboardStartRequest startRequest;

//     @BeforeEach
//     void setup() {
//         startRequest = new OnboardStartRequest();
//         startRequest.setBusinessName("Biz");
//         startRequest.setPhoneNumber("123");
//         startRequest.setAccessToken("token");
//         startRequest.setAppSecret("secret");
//     }

//     @Test
//     void startCreatesTenant() {
//         when(tenantConfigRepository.save(any(TenantConfig.class)))
//                 .thenAnswer(invocation -> invocation.getArgument(0));

//         var response = onboardingService.startOnboarding(startRequest);

//         ArgumentCaptor<TenantConfig> captor = ArgumentCaptor.forClass(TenantConfig.class);
//         verify(tenantConfigRepository).save(captor.capture());
//         assertThat(captor.getValue().getBusinessName()).isEqualTo("Biz");
//         assertThat(response.getTenantId()).isNotNull();
//     }


//     @Test
//     void verifyExistingTenant() {
//         UUID id = UUID.randomUUID();
//         OnboardVerifyRequest request = new OnboardVerifyRequest();
//         request.setTenantId(id);
//         request.setSmsCode("0000");
//         var response = onboardingService.verifyPhone(request);
//         assertThat(response.isSuccess()).isTrue();
//     }

//     @Test
//     void verifyNonExistingTenant() {
//         UUID id = UUID.randomUUID();
//         OnboardVerifyRequest request = new OnboardVerifyRequest();
//         request.setTenantId(id);
//         request.setSmsCode("0000");
//         var response = onboardingService.verifyPhone(request);
//         assertThat(response.isSuccess()).isTrue();
//     }
// }
