package com.whatsbot.broadcast.mapper;

import com.whatsbot.broadcast.dto.BroadcastCreateRequest;
import com.whatsbot.broadcast.dto.BroadcastMessageDto;
import com.whatsbot.broadcast.model.BroadcastMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BroadcastMapper {
    BroadcastMessageDto toDto(BroadcastMessage entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "status", expression = "java(com.whatsbot.broadcast.model.BroadcastStatus.PENDING)")
    @Mapping(target = "createdAt", ignore = true)
    BroadcastMessage toEntity(BroadcastCreateRequest request);
}
