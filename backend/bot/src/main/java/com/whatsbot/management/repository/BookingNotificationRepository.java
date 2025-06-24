package com.whatsbot.management.repository;

import com.whatsbot.auth.model.Tenant;
import com.whatsbot.management.model.BookingNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingNotificationRepository extends JpaRepository<BookingNotification, UUID> {
    List<BookingNotification> findByTenantOrderByCreatedAtDesc(Tenant tenant);
}
