package com.whatsbot.modules.booking.repository;

import com.whatsbot.modules.booking.model.Booking;
import com.whatsbot.modules.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findByIdAndContact_Id(UUID id, UUID userId);
    Optional<Booking> findFirstByContact_IdAndStatusOrderByDateDesc(UUID userId, BookingStatus status);
}
