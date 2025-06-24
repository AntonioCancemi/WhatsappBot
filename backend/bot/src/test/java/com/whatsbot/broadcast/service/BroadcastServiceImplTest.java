package com.whatsbot.broadcast.service;

import com.whatsbot.broadcast.dto.BroadcastCreateRequest;
import com.whatsbot.broadcast.dto.BroadcastMessageDto;
import com.whatsbot.broadcast.mapper.BroadcastMapper;
import com.whatsbot.broadcast.model.BroadcastMessage;
import com.whatsbot.broadcast.repository.BroadcastMessageRepository;
import com.whatsbot.broadcast.service.impl.BroadcastServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BroadcastServiceImplTest {

    @Mock
    private BroadcastMessageRepository repository;
    @Mock
    private BroadcastMapper mapper;
    @InjectMocks
    private BroadcastServiceImpl service;

    @Test
    void createSavesBroadcast() {
        BroadcastCreateRequest req = new BroadcastCreateRequest();
        req.setText("hello");
        req.setScheduledAt(Instant.now());
        BroadcastMessage entity = new BroadcastMessage();
        BroadcastMessage saved = new BroadcastMessage();
        BroadcastMessageDto dto = new BroadcastMessageDto();

        when(mapper.toEntity(req)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        BroadcastMessageDto result = service.create(req);

        assertThat(result).isEqualTo(dto);
        verify(repository).save(entity);
    }
}
