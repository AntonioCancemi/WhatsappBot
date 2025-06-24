package com.whatsbot.modules.management.service.impl;

import com.whatsbot.modules.auth.middleware.TenantContext;
import com.whatsbot.modules.auth.repository.TenantRepository;
import com.whatsbot.modules.management.dto.ServiceDto;
import com.whatsbot.modules.management.mapper.ServiceMapper;
import com.whatsbot.modules.management.model.ServiceOffering;
import com.whatsbot.modules.management.repository.ServiceOfferingRepository;
import com.whatsbot.modules.management.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final TenantRepository tenantRepository;
    private final ServiceOfferingRepository repository;
    private final ServiceMapper mapper;

    private com.whatsbot.modules.auth.model.Tenant tenant() {
        return tenantRepository.findById(TenantContext.get()).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceDto> list() {
        return repository.findByTenant(tenant()).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public ServiceDto create(ServiceDto dto) {
        ServiceOffering entity = mapper.toEntity(dto);
        entity.setTenant(tenant());
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public ServiceDto update(UUID id, ServiceDto dto) {
        ServiceOffering entity = repository.findById(id).orElseThrow();
        mapper.update(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
