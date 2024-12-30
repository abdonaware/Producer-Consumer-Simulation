package com.example.demo.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SumulationController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String processMessage(String message) {
        return "Received: " + message;
    }

}
