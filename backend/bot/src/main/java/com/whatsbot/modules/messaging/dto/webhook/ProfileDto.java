package com.whatsbot.modules.messaging.dto.webhook;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileDto {
    @NotBlank
    private String name;
}
