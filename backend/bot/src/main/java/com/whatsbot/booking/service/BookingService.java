package com.whatsbot.booking.service;

import com.whatsbot.booking.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface BookingService {
    Booking createBooking(UUID userId, LocalDate date, LocalTime time);
    void cancelBooking(UUID userId, UUID bookingId);
    void cancelLatestBookingForUser(UUID userId);
}
