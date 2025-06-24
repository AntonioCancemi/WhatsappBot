package com.whatsbot.modules.auth.controller;

import com.whatsbot.modules.auth.model.Tenant;
import com.whatsbot.modules.auth.repository.TenantRepository;
import com.whatsbot.modules.auth.middleware.TenantContext;
import com.whatsbot.modules.auth.exception.TenantNotFoundException;
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
