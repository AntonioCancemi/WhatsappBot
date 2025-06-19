package com.whatsbot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageDto {
    private Long id;

    @NotBlank
    private String text;
}
