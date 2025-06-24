package com.whatsbot.user.service.impl;


import com.whatsbot.user.intent.IntentClassifier;
import com.whatsbot.user.intent.IntentType;
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
