package com.whatsbot.repository;

import com.whatsbot.model.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantConfigRepository extends JpaRepository<TenantConfig, UUID> {
    Optional<TenantConfig> findByTenantId(String tenantId);
}
