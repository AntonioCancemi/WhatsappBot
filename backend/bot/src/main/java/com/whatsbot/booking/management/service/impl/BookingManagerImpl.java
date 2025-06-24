package com.whatsbot.booking.management.service.impl;

import com.whatsbot.auth.middleware.TenantContext;
import com.whatsbot.auth.model.Tenant;
import com.whatsbot.auth.repository.TenantRepository;
import com.whatsbot.booking.management.dto.AppointmentDto;
import com.whatsbot.booking.management.dto.SlotDto;
import com.whatsbot.booking.management.mapper.AppointmentMapper;
import com.whatsbot.booking.management.model.*;
import com.whatsbot.booking.management.repository.*;
import com.whatsbot.booking.management.service.BookingManager;
import com.whatsbot.user.model.User;
import com.whatsbot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingManagerImpl implements BookingManager {

    private final TenantRepository tenantRepository;
    private final ServiceOfferingRepository serviceRepository;
    private final AvailabilityRepository availabilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final BookingNotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    private Tenant currentTenant() {
        UUID id = TenantContext.get();
        return tenantRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public AppointmentDto create(UUID userId, UUID serviceId, LocalDate date, String timeStr) {
        Tenant tenant = currentTenant();
        ServiceOffering service = serviceRepository.findById(serviceId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        LocalTime time = LocalTime.parse(timeStr);

        if (appointmentRepository.existsByTenantAndServiceAndDateAndTime(tenant, service, date, time)) {
            throw new IllegalStateException("Slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setTenant(tenant);
        appointment.setUser(user);
        appointment.setService(service);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment = appointmentRepository.save(appointment);

        notificationRepository.save(new BookingNotification(null, tenant, "New booking request", null));
        return appointmentMapper.toDto(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SlotDto> availableSlots(UUID serviceId, LocalDate date) {
        Tenant tenant = currentTenant();
        ServiceOffering service = serviceRepository.findById(serviceId).orElseThrow();
        List<Availability> avail = availabilityRepository.findByTenantAndDayOfWeek(tenant, date.getDayOfWeek());
        List<Appointment> bookings = appointmentRepository.findByTenantAndServiceAndDate(tenant, service, date);
        List<SlotDto> slots = new ArrayList<>();
        for (Availability a : avail) {
            LocalTime t = a.getStartTime();
            while (!t.plusMinutes(service.getDurationMinutes()).isAfter(a.getEndTime())) {
                int booked = (int) bookings.stream().filter(b -> b.getTime().equals(t)).count();
                int remaining = service.getMaxPerSlot() - booked;
                if (remaining > 0) {
                    SlotDto dto = new SlotDto();
                    dto.time = t.toString();
                    dto.remaining = remaining;
                    slots.add(dto);
                }
                t = t.plusMinutes(service.getDurationMinutes() + service.getBufferMinutes());
            }
        }
        return slots;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> pending() {
        Tenant tenant = currentTenant();
        return appointmentRepository.findByTenantAndStatus(tenant, AppointmentStatus.PENDING)
                .stream().map(appointmentMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void approve(UUID bookingId) {
        Tenant tenant = currentTenant();
        Appointment app = appointmentRepository.findById(bookingId).orElseThrow();
        if (!app.getTenant().getId().equals(tenant.getId())) {
            throw new IllegalArgumentException("Invalid tenant");
        }
        app.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(app);
    }
}
