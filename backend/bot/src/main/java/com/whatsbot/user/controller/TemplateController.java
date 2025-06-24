package com.whatsbot.user.controller;

import com.whatsbot.user.dto.TemplateSendRequest;
import com.whatsbot.user.service.TemplateService;
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
        templateService.sendTemplate(request.getUserId(), request.getTemplateName());
        return ResponseEntity.accepted().build();
    }
}
