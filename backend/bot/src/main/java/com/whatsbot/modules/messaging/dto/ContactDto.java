package com.whatsbot.modules.messaging.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ContactDto {
    private UUID id;
    private String phone;
    private String name;
    private String lastMessage;
}
