package com.example.demo.Services;

import com.example.demo.Classes.Queue;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.ProjectRepository;

@Service
public class SimulationService {
    private ProjectRepository projectRepository = ProjectRepository.getInstance();

    public boolean checkIfSimulationCanStart() {
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

    public void startSimulation(int noOfProducts) {
        if (checkIfSimulationCanStart()) {
            projectRepository.startQueue.setPendingProduct(noOfProducts);
            projectRepository.startQueue.processProduct();

        }
    }
}
