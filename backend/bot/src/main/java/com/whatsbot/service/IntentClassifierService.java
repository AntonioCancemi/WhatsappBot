package com.whatsbot.service;

import com.whatsbot.intent.IntentType;

public interface IntentClassifierService {
    IntentType classify(String text);
}
