package com.whatsbot.modules.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TemplateSendRequest {
    @NotNull
    private UUID contactId;
    @NotBlank
    private String templateName;
}
