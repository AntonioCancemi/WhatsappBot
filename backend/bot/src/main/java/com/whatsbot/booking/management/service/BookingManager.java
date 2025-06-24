package com.whatsbot.booking.management.service;

import com.whatsbot.booking.management.dto.AppointmentDto;
import com.whatsbot.booking.management.dto.SlotDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingManager {
    AppointmentDto create(UUID userId, UUID serviceId, LocalDate date, String time);
    List<SlotDto> availableSlots(UUID serviceId, LocalDate date);
    List<AppointmentDto> pending();
    void approve(UUID bookingId);
}
