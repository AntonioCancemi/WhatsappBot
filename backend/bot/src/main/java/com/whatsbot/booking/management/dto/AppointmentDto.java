package com.whatsbot.booking.management.dto;

import java.util.UUID;

public class AppointmentDto {
    public UUID id;
    public UUID userId;
    public UUID serviceId;
    public String date;
    public String time;
    public String status;
}
