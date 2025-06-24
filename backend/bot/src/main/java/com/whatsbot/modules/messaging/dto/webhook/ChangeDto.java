package com.whatsbot.modules.messaging.dto.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeDto {
    @NotBlank
    private String field;

    @NotNull
    @Valid
    private ValueDto value;
}
