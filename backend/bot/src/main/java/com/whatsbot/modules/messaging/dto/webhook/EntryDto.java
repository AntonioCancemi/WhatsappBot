package com.whatsbot.modules.messaging.dto.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class EntryDto {
    @NotBlank
    private String id;

    @NotEmpty
    @Valid
    private List<ChangeDto> changes;
}
