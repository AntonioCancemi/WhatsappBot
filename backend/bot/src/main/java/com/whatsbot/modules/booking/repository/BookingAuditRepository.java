package com.whatsbot.modules.booking.repository;

import com.whatsbot.modules.booking.model.BookingAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingAuditRepository extends JpaRepository<BookingAudit, UUID> {
}
