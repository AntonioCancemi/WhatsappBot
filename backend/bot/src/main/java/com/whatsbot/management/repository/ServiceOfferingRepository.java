package com.whatsbot.management.repository;

import com.whatsbot.auth.model.Tenant;
import com.whatsbot.management.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, UUID> {
    List<ServiceOffering> findByTenant(Tenant tenant);
}
