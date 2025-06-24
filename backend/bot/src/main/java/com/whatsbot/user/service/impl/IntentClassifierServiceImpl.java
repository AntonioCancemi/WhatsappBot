package com.whatsbot.user.service.impl;

import com.whatsbot.intent.IntentClassifier;
import com.whatsbot.intent.IntentType;
import com.whatsbot.user.service.IntentClassifierService;
import org.springframework.stereotype.Service;

@Service
public class IntentClassifierServiceImpl implements IntentClassifierService {

    private final IntentClassifier classifier = new IntentClassifier();

    @Override
    public IntentType classify(String text) {
        return classifier.classify(text);
    }
}
