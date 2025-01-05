package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.Services.SimulationService;

@Controller
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Map<String, String> processMessage(Map<String, String> message) {
        // System.out.println("Received: " + message);
        if ("Start Simulation".equals(message.get("message"))) { // Corrected string comparison
            System.out.println("Starting simulation");
            return simulationService.startSimulation(Integer.parseInt(message.get("noOfProducts")));

        } else if ("Stop Simulation".equals(message.get("message"))) {
            System.out.println("Stopping simulation");
           return Map.of("message", "Simulation stopped");
        }
        return Map.of("message", "Invalid message");
    }
    @SendTo("/topic/messages")
    public Map<String, String> SendMessage(Map<String, String> message) {
        // System.out.println("Received: " + message);
        return message;
    }
    public void sendToWebSocket(Map<String, String> message) {
        SendMessage(message);
    }
}
