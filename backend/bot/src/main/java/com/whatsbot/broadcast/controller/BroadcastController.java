package com.whatsbot.broadcast.controller;

import com.whatsbot.broadcast.dto.BroadcastCreateRequest;
import com.whatsbot.broadcast.dto.BroadcastMessageDto;
import com.whatsbot.broadcast.service.BroadcastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/broadcasts")
@RequiredArgsConstructor
public class BroadcastController {

    private final BroadcastService broadcastService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BroadcastMessageDto create(@Valid @RequestBody BroadcastCreateRequest request) {
        return broadcastService.create(request);
    }

    @GetMapping
    public List<BroadcastMessageDto> list() {
        return broadcastService.findAll();
    }
}
