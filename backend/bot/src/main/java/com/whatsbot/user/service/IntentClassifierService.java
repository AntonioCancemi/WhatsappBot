package com.whatsbot.user.service;


import com.whatsbot.user.intent.IntentType;

public interface IntentClassifierService {
    IntentType classify(String text);
}
