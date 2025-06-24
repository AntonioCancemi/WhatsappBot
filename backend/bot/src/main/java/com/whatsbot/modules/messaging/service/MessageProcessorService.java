package com.whatsbot.modules.messaging.service;

public interface MessageProcessorService {
    void processIncomingMessage(String sender, String message, String sid);
}
