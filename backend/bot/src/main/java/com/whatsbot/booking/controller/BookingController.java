package com.whatsbot.booking.controller;

import com.whatsbot.booking.service.BookingService;
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
        bookingService.cancelBooking(request.getUserId(), request.getBookingId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Data
    private static class CancelRequest {
        @jakarta.validation.constraints.NotNull
        private UUID userId;
        @jakarta.validation.constraints.NotNull
        private UUID bookingId;
    }
}
