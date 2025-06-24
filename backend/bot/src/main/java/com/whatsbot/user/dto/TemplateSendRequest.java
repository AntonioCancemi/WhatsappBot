package com.whatsbot.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TemplateSendRequest {
    @NotNull
    private UUID userId;
    @NotBlank
    private String templateName;
}
