package com.whatsbot.modules.booking.service;

import com.whatsbot.modules.booking.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface BookingService {
    Booking createBooking(UUID contactId, LocalDate date, LocalTime time);
    void cancelBooking(UUID contactId, UUID bookingId);
    void cancelLatestBookingForContact(UUID contactId);
}
