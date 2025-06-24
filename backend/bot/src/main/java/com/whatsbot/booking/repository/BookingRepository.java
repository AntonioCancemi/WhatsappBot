package com.whatsbot.booking.repository;

import com.whatsbot.booking.model.Booking;
import com.whatsbot.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    Optional<Booking> findByIdAndUser_Id(UUID id, UUID userId);
    Optional<Booking> findFirstByUser_IdAndStatusOrderByDateDesc(UUID userId, BookingStatus status);
}
