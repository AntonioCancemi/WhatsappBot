package com.whatsbot.controller;

import com.whatsbot.dto.MessageDto;
import com.whatsbot.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDto> create(@Valid @RequestBody MessageDto messageDto) {
        return new ResponseEntity<>(messageService.save(messageDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<MessageDto> getAll() {
        return messageService.findAll();
    }
}
