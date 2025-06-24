package com.whatsbot.modules.booking.controller;

import com.whatsbot.modules.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancel(@Valid @RequestBody CancelRequest request) {
        bookingService.cancelBooking(request.getContactId(), request.getBookingId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Data
    private static class CancelRequest {
        @jakarta.validation.constraints.NotNull
        private UUID contactId;
        @jakarta.validation.constraints.NotNull
        private UUID bookingId;
    }
}
