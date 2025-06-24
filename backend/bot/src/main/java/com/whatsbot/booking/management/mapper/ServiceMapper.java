package com.whatsbot.booking.management.mapper;

import com.whatsbot.booking.management.dto.ServiceDto;
import com.whatsbot.booking.management.model.ServiceOffering;
import org.springframework.stereotype.Component;

/**
 * Simple mapper without MapStruct to convert ServiceOffering entities.
 */
@Component
public class ServiceMapper {

    public ServiceDto toDto(ServiceOffering entity) {
        if (entity == null) {
            return null;
        }
        ServiceDto dto = new ServiceDto();
        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.durationMinutes = entity.getDurationMinutes();
        dto.bufferMinutes = entity.getBufferMinutes();
        dto.maxPerSlot = entity.getMaxPerSlot();
        return dto;
    }

    public ServiceOffering toEntity(ServiceDto dto) {
        if (dto == null) {
            return null;
        }
        ServiceOffering entity = new ServiceOffering();
        entity.setId(dto.id);
        entity.setName(dto.name);
        entity.setDurationMinutes(dto.durationMinutes);
        entity.setBufferMinutes(dto.bufferMinutes);
        entity.setMaxPerSlot(dto.maxPerSlot);
        return entity;
    }

    public void update(ServiceDto dto, ServiceOffering entity) {
        if (dto == null || entity == null) {
            return;
        }
        if (dto.name != null) {
            entity.setName(dto.name);
        }
        entity.setDurationMinutes(dto.durationMinutes);
        entity.setBufferMinutes(dto.bufferMinutes);
        entity.setMaxPerSlot(dto.maxPerSlot);
    }
}
