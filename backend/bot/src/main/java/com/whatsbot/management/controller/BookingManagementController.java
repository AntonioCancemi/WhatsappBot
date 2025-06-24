package com.whatsbot.management.controller;

import com.whatsbot.management.dto.AppointmentDto;
import com.whatsbot.management.dto.SlotDto;
import com.whatsbot.management.service.BookingManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingManagementController {

    private final BookingManager bookingManager;

    @PostMapping("/create")
    public ResponseEntity<AppointmentDto> create(@RequestBody @NotNull CreateRequest request) {
        AppointmentDto dto = bookingManager.create(request.userId, request.serviceId, LocalDate.parse(request.date), request.time);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/slots")
    public List<SlotDto> slots(@RequestParam UUID serviceId, @RequestParam String date) {
        return bookingManager.availableSlots(serviceId, LocalDate.parse(date));
    }

    @GetMapping("/pending")
    public List<AppointmentDto> pending() {
        return bookingManager.pending();
    }

    @PostMapping("/approve")
    public void approve(@RequestBody ApproveRequest request) {
        bookingManager.approve(request.bookingId);
    }

    @Data
    private static class CreateRequest {
        @NotNull
        private UUID userId;
        @NotNull
        private UUID serviceId;
        @NotBlank
        private String date;
        @NotBlank
        private String time;
    }

    @Data
    private static class ApproveRequest {
        @NotNull
        private UUID bookingId;
    }
}
