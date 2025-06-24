package com.whatsbot.modules.booking.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsbot.modules.booking.dto.FlowStepResponse;
import com.whatsbot.modules.booking.model.ConversationState;
import com.whatsbot.modules.messaging.model.Contact;
import com.whatsbot.modules.booking.repository.ConversationStateRepository;
import com.whatsbot.modules.messaging.repository.ContactRepository;
import com.whatsbot.modules.booking.service.BookingService;
import com.whatsbot.modules.booking.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationStateRepository stateRepository;
    private final BookingService bookingService;
    private final ContactRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public FlowStepResponse startBooking(UUID contactId) {
        Contact contact = userRepository.findById(contactId).orElseThrow();
        ConversationState state = new ConversationState();
        state.setContact(user);
        state.setFlowId("booking");
        state.setStep("DATE");
        stateRepository.save(state);
        FlowStepResponse resp = new FlowStepResponse();
        resp.setStep("DATE");
        resp.setMessage("Per quale data desideri prenotare?");
        resp.setStateId(state.getId());
        return resp;
    }

    @Override
    @Transactional
    public FlowStepResponse next(UUID stateId, String userResponse) {
        ConversationState state = stateRepository.findById(stateId).orElseThrow();
        Map<String, String> data = new HashMap<>();
        if (state.getData() != null) {
            try {
                data = objectMapper.readValue(state.getData(), new TypeReference<>() {});
            } catch (Exception ignored) {
            }
        }
        switch (state.getStep()) {
            case "DATE" -> {
                data.put("date", userResponse);
                state.setStep("TIME");
                state.setData(writeData(data));
                stateRepository.save(state);
                return response(state, "TIME", "A che ora?");
            }
            case "TIME" -> {
                data.put("time", userResponse);
                state.setStep("CONFIRM");
                state.setData(writeData(data));
                stateRepository.save(state);
                return response(state, "CONFIRM", "Confermi prenotazione per " + data.get("date") + " alle " + data.get("time") + "? (Sì/No)");
            }
            case "CONFIRM" -> {
                if (userResponse.equalsIgnoreCase("s\u00ec") || userResponse.equalsIgnoreCase("si")) {
                    LocalDate date = LocalDate.parse(data.get("date"));
                    LocalTime time = LocalTime.parse(data.get("time"));
                    bookingService.createBooking(state.getContact().getId(), date, time);
                    state.setStep("DONE");
                    stateRepository.save(state);
                    return response(state, "DONE", "Prenotazione confermata");
                } else {
                    stateRepository.delete(state);
                    return response(state, "CANCELLED", "Prenotazione annullata");
                }
            }
            default -> throw new IllegalStateException("Invalid step");
        }
    }

    private String writeData(Map<String, String> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return "{}";
        }
    }

    private FlowStepResponse response(ConversationState state, String step, String message) {
        FlowStepResponse resp = new FlowStepResponse();
        resp.setStep(step);
        resp.setMessage(message);
        resp.setStateId(state.getId());
        return resp;
    }
}
