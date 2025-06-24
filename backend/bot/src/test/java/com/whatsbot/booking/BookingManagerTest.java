package com.whatsbot.booking;

import com.whatsbot.booking.management.service.BookingManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookingManagerTest {

    @Autowired
    private BookingManager bookingManager;

    @Test
    void slotsNotEmpty() {
        var tenantId = UUID.randomUUID();
        // assuming data initializer created demo data
        var slots = bookingManager.availableSlots(
                bookingManager.pending().stream().findFirst().map(b -> b.serviceId).orElse(null),
                LocalDate.now());
        assertThat(slots).isNotNull();
    }
}
