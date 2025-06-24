package com.whatsbot.user.service.impl;

import com.whatsbot.user.dto.MessageHistoryDto;
import com.whatsbot.user.dto.UserDto;
import com.whatsbot.user.repository.MessageRepository;
import com.whatsbot.user.repository.UserRepository;
import com.whatsbot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(u -> {
                    UserDto dto = new UserDto();
                    dto.setId(u.getId());
                    dto.setPhone(u.getPhone());
                    dto.setName(u.getName());
                    messageRepository.findFirstByUser_IdOrderByTimestampDesc(u.getId())
                            .ifPresent(m -> dto.setLastMessage(m.getText()));
                    return dto;
                })
                .toList();
    }

    @Override
    public List<MessageHistoryDto> getMessages(UUID userId) {
        return messageRepository.findByUser_IdOrderByTimestampAsc(userId).stream()
                .map(m -> {
                    MessageHistoryDto dto = new MessageHistoryDto();
                    dto.setId(m.getId());
                    dto.setUserId(userId);
                    dto.setText(m.getText());
                    dto.setDirection(m.getDirection());
                    dto.setTimestamp(m.getTimestamp());
                    return dto;
                })
                .toList();
    }
}
