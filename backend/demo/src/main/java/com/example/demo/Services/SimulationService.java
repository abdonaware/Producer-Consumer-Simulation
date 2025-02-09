package com.example.demo.Services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.ProjectRepository;

@Service
public class SimulationService {
    @Autowired
    private ProjectRepository projectRepository;

    public boolean checkIfSimulationCanStart() {
        if (projectRepository.machines.size() == 0) {
            System.out.println("Machines or Queues are not present");
            return false;
        }
        for (int i = 0; i < projectRepository.machines.size(); i++) {
            if (projectRepository.machines.get(i).getInQueues().size() == 0) {
                return false;
            }
            if (projectRepository.machines.get(i).getOutQueues().size() == 0) {
                return false;
            }
        }
        if (!projectRepository.startQueue.getOutMashines().isEmpty()) {
            return false;
        }
        if (!projectRepository.endQueue.getInMashines().isEmpty()) {
            return false;
        }
        for (int i = 0; i < projectRepository.queues.size(); i++) {
            if (projectRepository.queues.get(i).getInMashines().size() == 0) {
                return false;
            }
            if (projectRepository.queues.get(i).getOutMashines().size() == 0) {
                return false;
            }
        }
        return true;
    }

    public Map<String, String> startSimulation(int noOfProducts) {
        if (checkIfSimulationCanStart()) {
            projectRepository.saveSnapShot();
            projectRepository.startQueue.setPendingProduct(noOfProducts);
            for (int i = 0; i < noOfProducts; i++) {
                int R = ((i + 1) * 123) % 256;
                int G = ((i + 1) * 456) % 256;
                int B = ((i + 1) * 789) % 256;
                projectRepository.startQueue.getProducts().add(String.format("#%02X%02X%02X", R, G, B));
            }

            projectRepository.endQueue.setPendingProduct(0);
            projectRepository.startQueue.processProduct();
            Map<String, String> response = Map.of("message", "Simulation started");
            return response;
        } else {
            // response.replace("message", "Simulation can't start");
            return Map.of("message", "Simulation can't start");
        }
    }
}
