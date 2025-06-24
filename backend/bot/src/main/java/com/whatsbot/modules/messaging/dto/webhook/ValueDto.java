package com.whatsbot.modules.messaging.dto.webhook;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class ValueDto {
    @Valid
    private List<ContactDto> contacts;

    @Valid
    private List<WhatsappMessageDto> messages;
}
