package com.whatsbot.modules.auth.dto;

public record LoginResponse(String token, UserDto user) {}
