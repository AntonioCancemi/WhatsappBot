package com.whatsbot.auth.controller;

import com.whatsbot.auth.model.Tenant;
import com.whatsbot.auth.repository.TenantRepository;
import com.whatsbot.auth.middleware.TenantContext;
import com.whatsbot.auth.exception.TenantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantRepository tenantRepository;

    @GetMapping("/current")
    public Tenant current() {
        UUID id = TenantContext.get();
        return tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }
}
