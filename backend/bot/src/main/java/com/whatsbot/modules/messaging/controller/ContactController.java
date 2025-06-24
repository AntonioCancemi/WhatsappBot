package com.whatsbot.modules.messaging.controller;

import com.whatsbot.modules.messaging.dto.MessageHistoryDto;
import com.whatsbot.modules.messaging.dto.ContactDto;
import com.whatsbot.modules.messaging.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public List<ContactDto> getAll() {
        return contactService.findAll();
    }

    @GetMapping("/{id}/messages")
    public List<MessageHistoryDto> getMessages(@PathVariable UUID id) {
        return contactService.getMessages(id);
    }
}
