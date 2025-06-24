package com.whatsbot.management.repository;

import com.whatsbot.auth.model.Tenant;
import com.whatsbot.management.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {
    List<Availability> findByTenantAndDayOfWeek(Tenant tenant, DayOfWeek dayOfWeek);
}
