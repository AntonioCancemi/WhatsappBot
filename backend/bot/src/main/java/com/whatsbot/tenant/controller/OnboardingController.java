package com.whatsbot.tenant.controller;

import com.whatsbot.tenant.dto.OnboardStartRequest;
import com.whatsbot.tenant.dto.OnboardStartResponse;
import com.whatsbot.tenant.dto.OnboardVerifyRequest;
import com.whatsbot.tenant.dto.OnboardVerifyResponse;
import com.whatsbot.tenant.service.OnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles onboarding flow for new tenants.
 */
@RestController
@RequestMapping("/onboard")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping(
        path = "/start",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OnboardStartResponse> start(@Valid @RequestBody OnboardStartRequest request) {
        OnboardStartResponse response = onboardingService.startOnboarding(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(
        path = "/verify",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OnboardVerifyResponse> verify(@Valid @RequestBody OnboardVerifyRequest request) {
        OnboardVerifyResponse response = onboardingService.verifyPhone(request);
        return ResponseEntity.ok(response);
    }
}
