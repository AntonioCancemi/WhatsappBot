package com.whatsbot.modules.management.mapper;

import com.whatsbot.modules.management.dto.AppointmentDto;
import com.whatsbot.modules.management.model.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDto toDto(Appointment entity) {
        if (entity == null) {
            return null;
        }
        AppointmentDto dto = new AppointmentDto();
        dto.id = entity.getId();
        dto.userId = entity.getUser().getId();
        dto.serviceId = entity.getService().getId();
        dto.date = entity.getDate().toString();
        dto.time = entity.getTime().toString();
        dto.status = entity.getStatus().name();
        return dto;
    }
}
