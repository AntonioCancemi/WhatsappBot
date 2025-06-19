package com.whatsbot.dto.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WhatsappMessageDto {
    @NotBlank
    private String from;

    @Valid
    private WhatsappTextDto text;
}
