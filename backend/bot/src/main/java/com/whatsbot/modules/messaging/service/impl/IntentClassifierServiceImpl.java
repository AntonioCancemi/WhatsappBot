package com.whatsbot.modules.messaging.service.impl;


import com.whatsbot.modules.messaging.intent.IntentClassifier;
import com.whatsbot.modules.messaging.intent.IntentType;
import com.whatsbot.modules.messaging.service.IntentClassifierService;
import org.springframework.stereotype.Service;

@Service
public class IntentClassifierServiceImpl implements IntentClassifierService {

    private final IntentClassifier classifier = new IntentClassifier();

    @Override
    public IntentType classify(String text) {
        return classifier.classify(text);
    }
}
