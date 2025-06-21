package com.whatsbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.model.User;
import com.whatsbot.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setPhone("123");
        user.setLanguage("it");
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    void startFlow() throws Exception {
        String payload = objectMapper.writeValueAsString(new Start(userId));
        mockMvc.perform(post("/conversation/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());
    }

    private record Start(UUID userId) {}
}
