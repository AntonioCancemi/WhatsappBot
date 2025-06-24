package com.whatsbot.modules.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageDto {
    private UUID id;

    @NotBlank
    private String text;

    private String intent;
}
