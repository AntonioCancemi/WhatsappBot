package com.whatsbot.controller;

import com.whatsbot.dto.OnboardStartRequest;
import com.whatsbot.dto.OnboardStartResponse;
import com.whatsbot.dto.OnboardVerifyRequest;
import com.whatsbot.dto.OnboardVerifyResponse;
import com.whatsbot.service.OnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/start")
    public ResponseEntity<OnboardStartResponse> start(@Valid @RequestBody OnboardStartRequest request) {
        OnboardStartResponse response = onboardingService.start(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<OnboardVerifyResponse> verify(@Valid @RequestBody OnboardVerifyRequest request) {
        OnboardVerifyResponse response = onboardingService.verify(request);
        if (response.isVerified()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
