package com.whatsbot.modules.management.mapper;

import com.whatsbot.modules.management.dto.AvailabilityDto;
import com.whatsbot.modules.management.model.Availability;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityMapper {

    public AvailabilityDto toDto(Availability entity) {
        if (entity == null) {
            return null;
        }
        AvailabilityDto dto = new AvailabilityDto();
        dto.id = entity.getId();
        dto.dayOfWeek = entity.getDayOfWeek().name();
        dto.startTime = entity.getStartTime().toString();
        dto.endTime = entity.getEndTime().toString();
        return dto;
    }

    public Availability toEntity(AvailabilityDto dto) {
        if (dto == null) {
            return null;
        }
        Availability entity = new Availability();
        entity.setId(dto.id);
        entity.setDayOfWeek(java.time.DayOfWeek.valueOf(dto.dayOfWeek));
        entity.setStartTime(java.time.LocalTime.parse(dto.startTime));
        entity.setEndTime(java.time.LocalTime.parse(dto.endTime));
        return entity;
    }

    public void update(AvailabilityDto dto, Availability entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.dayOfWeek != null) {
            entity.setDayOfWeek(java.time.DayOfWeek.valueOf(dto.dayOfWeek));
        }
        if (dto.startTime != null) {
            entity.setStartTime(java.time.LocalTime.parse(dto.startTime));
        }
        if (dto.endTime != null) {
            entity.setEndTime(java.time.LocalTime.parse(dto.endTime));
        }
    }
}
