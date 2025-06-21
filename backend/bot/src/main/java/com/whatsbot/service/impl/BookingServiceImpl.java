package com.whatsbot.service.impl;

import com.whatsbot.model.*;
import com.whatsbot.repository.BookingAuditRepository;
import com.whatsbot.repository.BookingRepository;
import com.whatsbot.repository.UserRepository;
import com.whatsbot.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingAuditRepository bookingAuditRepository;
    private final UserRepository userRepository;

    @Override
    public Booking createBooking(UUID userId, LocalDate date, LocalTime time) {
        User user = userRepository.findById(userId).orElseThrow();
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setDate(date);
        booking.setTime(time);
        booking.setStatus(BookingStatus.CONFIRMED);
        Booking saved = bookingRepository.save(booking);
        bookingAuditRepository.save(new BookingAudit(null, saved, "CREATED", null));
        return saved;
    }

    @Override
    public void cancelBooking(UUID userId, UUID bookingId) {
        Booking booking = bookingRepository.findByIdAndUser_Id(bookingId, userId).orElseThrow();
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return;
        }
        if (booking.getDate().atTime(booking.getTime()).isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Booking is in the past");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        bookingAuditRepository.save(new BookingAudit(null, booking, "CANCELLED", null));
    }

    @Override
    public void cancelLatestBookingForUser(UUID userId) {
        bookingRepository.findFirstByUser_IdAndStatusOrderByDateDesc(userId, BookingStatus.CONFIRMED)
                .ifPresent(b -> cancelBooking(userId, b.getId()));
    }
}
