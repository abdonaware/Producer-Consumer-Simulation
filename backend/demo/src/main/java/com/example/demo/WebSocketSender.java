package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSender {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Sends a message to the specified WebSocket topic.
     *
     * @param topic   the WebSocket topic to send the message to
     * @param message the message to send
     */
    public void sendMessage(String topic, String message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
