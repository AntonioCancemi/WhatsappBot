package com.whatsbot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.model.User;
import com.whatsbot.model.Booking;
import com.whatsbot.repository.UserRepository;
import com.whatsbot.repository.BookingRepository;
import com.whatsbot.repository.ConversationStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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
    private BookingRepository bookingRepository;
    @Autowired
    private ConversationStateRepository conversationStateRepository;
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

    @Test
    void completeFlow() throws Exception {
        String payload = objectMapper.writeValueAsString(new Start(userId));
        String response = mockMvc.perform(post("/conversation/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        UUID stateId = objectMapper.readValue(response, Step.class).stateId();

        mockMvc.perform(post("/conversation/" + stateId + "/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Next("2024-01-01"))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/conversation/" + stateId + "/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Next("10:00"))))
                .andExpect(status().isOk());

        mockMvc.perform(post("/conversation/" + stateId + "/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Next("Sì"))))
                .andExpect(status().isOk());

        Booking booking = bookingRepository.findAll().get(0);
        assertThat(booking.getUser().getId()).isEqualTo(userId);
    }

    @Test
    void unknownStateReturns404() throws Exception {
        UUID random = UUID.randomUUID();
        mockMvc.perform(post("/conversation/" + random + "/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Next("test"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void invalidStepReturns400() throws Exception {
        var user = userRepository.findById(userId).orElseThrow();
        var state = new com.whatsbot.model.ConversationState();
        state.setUser(user);
        state.setFlowId("booking");
        state.setStep("UNKNOWN");
        state = conversationStateRepository.save(state);

        mockMvc.perform(post("/conversation/" + state.getId() + "/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Next("test"))))
                .andExpect(status().isBadRequest());
    }

    private record Step(UUID stateId, String step, String message){}
    private record Next(String userResponse){}
}
