package com.whatsbot.user.service;

public interface MessageProcessorService {
    void processIncomingMessage(String sender, String message, String sid);
}
