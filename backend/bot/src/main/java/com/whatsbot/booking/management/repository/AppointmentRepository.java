package com.whatsbot.booking.management.repository;

import com.whatsbot.auth.model.Tenant;
import com.whatsbot.booking.management.model.Appointment;
import com.whatsbot.booking.management.model.AppointmentStatus;
import com.whatsbot.booking.management.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByTenantAndServiceAndDate(Tenant tenant, ServiceOffering service, LocalDate date);
    List<Appointment> findByTenantAndStatus(Tenant tenant, AppointmentStatus status);
    boolean existsByTenantAndServiceAndDateAndTime(Tenant tenant, ServiceOffering service, LocalDate date, LocalTime time);
}
