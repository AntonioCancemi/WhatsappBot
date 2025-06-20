package com.whatsbot.dto.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class WebhookRequestDto {
    @NotBlank
    private String object;

    @NotEmpty
    @Valid
    private List<EntryDto> entry;
}
