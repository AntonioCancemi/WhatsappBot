package com.whatsbot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.whatsbot.dto.FlowStepRequest;
import com.whatsbot.dto.FlowStepResponse;
import com.whatsbot.flow.FlowDefinition;
import com.whatsbot.flow.FlowStep;
import com.whatsbot.flow.FlowsConfig;
import com.whatsbot.model.ConversationState;
import com.whatsbot.model.User;
import com.whatsbot.repository.ConversationStateRepository;
import com.whatsbot.repository.UserRepository;
import com.whatsbot.service.ConversationalFlowService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ConversationalFlowServiceImpl implements ConversationalFlowService {

    private static final Logger log = LoggerFactory.getLogger(ConversationalFlowServiceImpl.class);
    private final UserRepository userRepository;
    private final ConversationStateRepository stateRepository;
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    private Map<String, FlowDefinition> flows = new HashMap<>();

    @PostConstruct
    void init() {
        try {
            FlowsConfig config = objectMapper.readValue(new ClassPathResource("flows.yml").getInputStream(), FlowsConfig.class);
            if (config.getFlows() != null) {
                config.getFlows().forEach(f -> flows.put(f.getId(), f));
            }
        } catch (IOException e) {
            log.error("Failed to load flows configuration", e);
        }
    }

    @Override
    public List<String> listFlows() {
        return new ArrayList<>(flows.keySet());
    }

    @Override
    public FlowStepResponse nextStep(String flowId, FlowStepRequest request) {
        FlowDefinition definition = flows.get(flowId);
        if (definition == null) {
            throw new IllegalArgumentException("Unknown flow: " + flowId);
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        ConversationState state = stateRepository.findByUser_IdAndFlowId(user.getId(), flowId)
                .orElseGet(() -> {
                    ConversationState cs = new ConversationState();
                    cs.setUser(user);
                    cs.setFlowId(flowId);
                    cs.setStep("start");
                    return cs;
                });

        String currentStepId = state.getStep();
        FlowStep current = definition.getSteps().stream()
                .filter(s -> s.getId().equals(currentStepId))
                .findFirst()
                .orElseThrow();

        String nextStepId = current.getTransitions() != null
                ? current.getTransitions().getOrDefault(Optional.ofNullable(request.getIntent()).orElse(""), null)
                : null;
        if (nextStepId == null && current.getTransitions() != null) {
            nextStepId = current.getTransitions().get("next");
        }
        if (nextStepId == null) {
            nextStepId = currentStepId; // repeat same step if no transition
        }

        state.setStep(nextStepId);
        if (request.getParams() != null) {
            try {
                state.setData(objectMapper.writeValueAsString(request.getParams()));
            } catch (Exception e) {
                log.warn("Failed to serialize params", e);
            }
        }
        stateRepository.save(state);

        FlowStep next = definition.getSteps().stream()
                .filter(s -> s.getId().equals(nextStepId))
                .findFirst()
                .orElseThrow();

        FlowStepResponse resp = new FlowStepResponse();
        resp.setStep(nextStepId);
        resp.setMessage(next.getMessage());
        return resp;
    }
}
