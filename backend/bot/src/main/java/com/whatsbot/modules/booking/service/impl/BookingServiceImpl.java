package com.whatsbot.modules.booking.service.impl;

import com.whatsbot.modules.booking.model.Booking;
import com.whatsbot.modules.booking.model.BookingAudit;
import com.whatsbot.modules.booking.model.BookingStatus;
import com.whatsbot.modules.messaging.model.Contact;
import com.whatsbot.modules.booking.repository.BookingAuditRepository;
import com.whatsbot.modules.booking.repository.BookingRepository;
import com.whatsbot.modules.messaging.repository.ContactRepository;
import com.whatsbot.modules.booking.service.BookingService;
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
    private final ContactRepository contactRepository;

    @Override
    public Booking createBooking(UUID contactId, LocalDate date, LocalTime time) {
        Contact user = contactRepository.findById(contactId).orElseThrow();
        Booking booking = new Booking();
        booking.setContact(user);
        booking.setDate(date);
        booking.setTime(time);
        booking.setStatus(BookingStatus.CONFIRMED);
        Booking saved = bookingRepository.save(booking);
        bookingAuditRepository.save(new BookingAudit(null, saved, "CREATED", null));
        return saved;
    }

    @Override
    public void cancelBooking(UUID contactId, UUID bookingId) {
        Booking booking = bookingRepository.findByIdAndContact_Id(bookingId, contactId).orElseThrow();
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
    public void cancelLatestBookingForContact(UUID contactId) {
        bookingRepository.findFirstByContact_IdAndStatusOrderByDateDesc(contactId, BookingStatus.CONFIRMED)
                .ifPresent(b -> cancelBooking(contactId, b.getId()));
    }
}
