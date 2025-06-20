package com.whatsbot.service;

public interface MessageProcessorService {
    void processIncomingMessage(String sender, String message);
}
