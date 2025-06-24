package com.whatsbot.management.init;

import com.whatsbot.auth.model.Tenant;
import com.whatsbot.auth.repository.TenantRepository;
import com.whatsbot.management.model.Availability;
import com.whatsbot.management.model.ServiceOffering;
import com.whatsbot.management.repository.AvailabilityRepository;
import com.whatsbot.management.repository.ServiceOfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class BookingDataInitializer implements CommandLineRunner {

    private final TenantRepository tenantRepository;
    private final ServiceOfferingRepository serviceRepository;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public void run(String... args) {
        if (serviceRepository.count() > 0) {
            return;
        }
        Tenant tenant = tenantRepository.findByName("demo").orElseThrow();
        ServiceOffering service = new ServiceOffering(null, tenant, "Consultation", 30, 10, 1);
        service = serviceRepository.save(service);
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.getValue() <= 5) {
                Availability a = new Availability(null, tenant, day,
                        LocalTime.of(9, 0), LocalTime.of(17, 0));
                availabilityRepository.save(a);
            }
        }
    }
}
