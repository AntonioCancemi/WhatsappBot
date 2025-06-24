package com.whatsbot.modules.tenant.repository;

import com.whatsbot.modules.tenant.model.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantConfigRepository extends JpaRepository<TenantConfig, UUID> {
}
