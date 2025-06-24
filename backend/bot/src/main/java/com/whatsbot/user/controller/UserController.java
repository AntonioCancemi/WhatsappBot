package com.whatsbot.user.controller;

import com.whatsbot.user.dto.MessageHistoryDto;
import com.whatsbot.user.dto.UserDto;
import com.whatsbot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}/messages")
    public List<MessageHistoryDto> getMessages(@PathVariable UUID id) {
        return userService.getMessages(id);
    }
}
