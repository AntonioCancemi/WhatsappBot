package com.whatsbot.broadcast.service;

import com.whatsbot.broadcast.dto.BroadcastCreateRequest;
import com.whatsbot.broadcast.dto.BroadcastMessageDto;

import java.util.List;

public interface BroadcastService {
    BroadcastMessageDto create(BroadcastCreateRequest request);

    List<BroadcastMessageDto> findAll();
}
