package com.whatsbot.auth.dto;

public record LoginRequest(String username, String password, String tenant) {
}
