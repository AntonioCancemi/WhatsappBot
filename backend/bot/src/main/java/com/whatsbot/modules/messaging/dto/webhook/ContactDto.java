package com.whatsbot.modules.messaging.dto.webhook;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactDto {
    @NotBlank
    private String wa_id;

    @Valid
    private ProfileDto profile;
}
