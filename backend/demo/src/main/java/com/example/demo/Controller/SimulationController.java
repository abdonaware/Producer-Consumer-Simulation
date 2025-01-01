package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.Services.SimulationService;

@Controller
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String processMessage(String message) {
        System.out.println("Received: " + message);
        if ("Start Simulation".equals(message)) { // Corrected string comparison
            System.out.println("Starting simulation");
            return simulationService.startSimulation(5);
            
        } else if ("Stop Simulation".equals(message)) {
            System.out.println("Stopping simulation");
            return "Simulation stopped";
        }
        return "Received: " + message;
    }
}
