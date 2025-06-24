package com.whatsbot.auth.dto;

public record LoginResponse(String token, UserDto user) {}
