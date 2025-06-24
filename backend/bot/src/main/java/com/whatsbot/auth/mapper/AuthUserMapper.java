package com.whatsbot.auth.mapper;

import com.whatsbot.auth.dto.UserDto;
import com.whatsbot.auth.model.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUserMapper INSTANCE = Mappers.getMapper(AuthUserMapper.class);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(r -> r.getName()).toList())")
    UserDto toDto(AuthUser user);
}
