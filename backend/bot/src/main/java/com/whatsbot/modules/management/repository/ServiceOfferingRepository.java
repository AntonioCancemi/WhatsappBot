package com.whatsbot.modules.management.repository;

import com.whatsbot.modules.auth.model.Tenant;
import com.whatsbot.modules.management.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, UUID> {
    List<ServiceOffering> findByTenant(Tenant tenant);
}
