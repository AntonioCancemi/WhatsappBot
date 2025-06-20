package com.whatsbot.dto.webhook;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileDto {
    @NotBlank
    private String name;
}
