package com.whatsbot.broadcast.service.impl;

import com.whatsbot.broadcast.dto.BroadcastCreateRequest;
import com.whatsbot.broadcast.dto.BroadcastMessageDto;
import com.whatsbot.broadcast.mapper.BroadcastMapper;
import com.whatsbot.broadcast.model.BroadcastMessage;
import com.whatsbot.broadcast.repository.BroadcastMessageRepository;
import com.whatsbot.broadcast.service.BroadcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {

    private final BroadcastMessageRepository repository;
    private final BroadcastMapper mapper;

    @Override
    public BroadcastMessageDto create(BroadcastCreateRequest request) {
        BroadcastMessage entity = mapper.toEntity(request);
        entity.setTenantId(UUID.randomUUID());
        BroadcastMessage saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<BroadcastMessageDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
