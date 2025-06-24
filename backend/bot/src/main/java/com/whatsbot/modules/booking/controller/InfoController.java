package com.whatsbot.modules.booking.controller;

import com.whatsbot.modules.booking.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    @GetMapping("/open-hours")
    public Map<String, String> openHours() {
        return infoService.getOpenHours();
    }
}
