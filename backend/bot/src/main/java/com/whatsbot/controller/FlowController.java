package com.whatsbot.controller;

import com.whatsbot.dto.FlowStepRequest;
import com.whatsbot.dto.FlowStepResponse;
import com.whatsbot.service.ConversationalFlowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flows")
@RequiredArgsConstructor
public class FlowController {

    private final ConversationalFlowService flowService;

    @GetMapping
    public List<String> listFlows() {
        return flowService.listFlows();
    }

    @PostMapping("/{flowId}/step")
    public ResponseEntity<FlowStepResponse> nextStep(@PathVariable String flowId,
                                                     @Valid @RequestBody FlowStepRequest request) {
        return new ResponseEntity<>(flowService.nextStep(flowId, request), HttpStatus.OK);
    }
}
