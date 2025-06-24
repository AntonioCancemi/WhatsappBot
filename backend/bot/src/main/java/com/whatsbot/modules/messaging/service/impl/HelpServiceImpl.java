package com.whatsbot.modules.messaging.service.impl;


import com.whatsbot.modules.messaging.intent.IntentType;
import com.whatsbot.modules.messaging.service.HelpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService {

    @Override
    public Map<String, String> getHelp() {
        Map<String, String> help = new LinkedHashMap<>();
        for (IntentType type : IntentType.values()) {
            help.put(type.name(), type.name().toLowerCase());
        }
        return help;
    }
}
