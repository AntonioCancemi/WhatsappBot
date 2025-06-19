package com.whatsbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageDto {
    private UUID id;

    @NotBlank
    private String text;
}
