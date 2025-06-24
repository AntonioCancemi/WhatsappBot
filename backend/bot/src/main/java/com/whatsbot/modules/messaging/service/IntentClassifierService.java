package com.whatsbot.modules.messaging.service;


import com.whatsbot.modules.messaging.intent.IntentType;

public interface IntentClassifierService {
    IntentType classify(String text);
}
