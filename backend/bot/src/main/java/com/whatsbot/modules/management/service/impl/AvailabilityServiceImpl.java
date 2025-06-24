package com.whatsbot.modules.management.service.impl;

import com.whatsbot.modules.auth.middleware.TenantContext;
import com.whatsbot.modules.auth.repository.TenantRepository;
import com.whatsbot.modules.management.dto.AvailabilityDto;
import com.whatsbot.modules.management.mapper.AvailabilityMapper;
import com.whatsbot.modules.management.model.Availability;
import com.whatsbot.modules.management.repository.AvailabilityRepository;
import com.whatsbot.modules.management.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final TenantRepository tenantRepository;
    private final AvailabilityRepository repository;
    private final AvailabilityMapper mapper;

    private com.whatsbot.modules.auth.model.Tenant tenant() {
        return tenantRepository.findById(TenantContext.get()).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDto> list() {
        return repository.findAll().stream()
                .filter(a -> a.getTenant().getId().equals(tenant().getId()))
                .map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public AvailabilityDto create(AvailabilityDto dto) {
        Availability entity = mapper.toEntity(dto);
        entity.setTenant(tenant());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public AvailabilityDto update(UUID id, AvailabilityDto dto) {
        Availability entity = repository.findById(id).orElseThrow();
        mapper.update(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
