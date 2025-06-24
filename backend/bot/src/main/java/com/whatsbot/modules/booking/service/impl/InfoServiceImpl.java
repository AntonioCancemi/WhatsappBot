package com.whatsbot.modules.booking.service.impl;

import com.whatsbot.modules.booking.config.InfoProperties;
import com.whatsbot.modules.booking.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoProperties infoProperties;

    @Override
    public Map<String, String> getOpenHours() {
        return infoProperties.getOpenHours();
    }
}
