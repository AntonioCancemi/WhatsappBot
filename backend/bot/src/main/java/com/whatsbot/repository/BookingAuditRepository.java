package com.whatsbot.repository;

import com.whatsbot.model.BookingAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingAuditRepository extends JpaRepository<BookingAudit, UUID> {
}
