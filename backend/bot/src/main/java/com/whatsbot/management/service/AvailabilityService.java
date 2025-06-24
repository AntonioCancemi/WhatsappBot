package com.whatsbot.management.service;

import com.whatsbot.management.dto.AvailabilityDto;

import java.util.List;
import java.util.UUID;

public interface AvailabilityService {
    List<AvailabilityDto> list();
    AvailabilityDto create(AvailabilityDto dto);
    AvailabilityDto update(UUID id, AvailabilityDto dto);
    void delete(UUID id);
}
