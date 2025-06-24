package com.whatsbot.modules.auth.dto;

public record RegisterRequest(String email, String password, String fullName, String tenantName) {}
