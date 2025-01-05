package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketSender {

    @Autowired
    private final  SimpMessagingTemplate messagingTemplate;
    // private MessageChannel messageChannel;

    public WebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        messagingTemplate.setSendTimeout(100);
       
    }

    

    /**
     * Sends a message to the specified WebSocket topic.
     *
     * @param topic the WebSocket topic to send the message to
     * @param message the message to send
     */
    public void sendMessage(String topic, Map<String, String> message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
