package com.whatsbot.controller;

import com.whatsbot.dto.OnboardStartResponse;
import com.whatsbot.dto.OnboardVerifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles onboarding flow for new tenants.
 */
@RestController
@RequestMapping("/onboard")
@RequiredArgsConstructor
public class OnboardingController {

    @PostMapping("/start")
    public ResponseEntity<OnboardStartResponse> start() {
        return ResponseEntity.ok(new OnboardStartResponse());
    }

    @PostMapping("/verify")
    public ResponseEntity<OnboardVerifyResponse> verify() {
        return ResponseEntity.ok(new OnboardVerifyResponse());
    }
}
