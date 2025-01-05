package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSender {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    private WebSocketSender Instance;

    public WebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.messagingTemplate.setSendTimeout(100);
    }

    public WebSocketSender getInstance() {
        if (Instance == null) {
            Instance = new WebSocketSender(messagingTemplate);
        }
        return Instance;
    }

    public void sendMessage(String topic, Map<String, String> message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
