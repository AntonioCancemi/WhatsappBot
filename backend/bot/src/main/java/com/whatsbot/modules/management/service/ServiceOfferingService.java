package com.whatsbot.modules.management.service;

import com.whatsbot.modules.management.dto.ServiceDto;

import java.util.List;
import java.util.UUID;

public interface ServiceOfferingService {
    List<ServiceDto> list();
    ServiceDto create(ServiceDto dto);
    ServiceDto update(UUID id, ServiceDto dto);
    void delete(UUID id);
}
