package com.whatsbot.booking.management.dto;

import java.util.UUID;

public class ServiceDto {
    public UUID id;
    public String name;
    public int durationMinutes;
    public int bufferMinutes;
    public int maxPerSlot;
}
