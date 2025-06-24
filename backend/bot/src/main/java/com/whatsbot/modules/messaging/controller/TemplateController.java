package com.whatsbot.modules.messaging.controller;

import com.whatsbot.modules.messaging.dto.TemplateSendRequest;
import com.whatsbot.modules.messaging.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@Valid @RequestBody TemplateSendRequest request) {
        templateService.sendTemplate(request.getContactId(), request.getTemplateName());
        return ResponseEntity.accepted().build();
    }
}
