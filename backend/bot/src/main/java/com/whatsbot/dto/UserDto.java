package com.whatsbot.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String phone;
    private String name;
    private String lastMessage;
}
