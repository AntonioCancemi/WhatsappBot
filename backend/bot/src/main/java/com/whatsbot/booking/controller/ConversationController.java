package com.whatsbot.booking.controller;

import com.whatsbot.booking.dto.FlowStepResponse;
import com.whatsbot.booking.service.ConversationService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/start")
    public ResponseEntity<FlowStepResponse> start(@Valid @RequestBody StartRequest request) {
        return new ResponseEntity<>(conversationService.startBooking(request.getUserId()), HttpStatus.CREATED);
    }

    @PostMapping("/{stateId}/next")
    public FlowStepResponse next(@PathVariable UUID stateId, @Valid @RequestBody NextRequest request) {
        return conversationService.next(stateId, request.getUserResponse());
    }

    @Data
    private static class StartRequest {
        @jakarta.validation.constraints.NotNull
        private UUID userId;
    }

    @Data
    private static class NextRequest {
        @jakarta.validation.constraints.NotBlank
        private String userResponse;
    }
}
