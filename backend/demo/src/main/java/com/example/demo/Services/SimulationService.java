package com.example.demo.Services;

import org.springframework.stereotype.Service;

import com.example.demo.ProjectRepository;

@Service
public class SimulationService {
    private ProjectRepository projectRepository = ProjectRepository.getInstance();

    public boolean checkIfSimulationCanStart() {
        if(projectRepository.machines.size() == 0 || projectRepository.queues.size() == 0){
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

    public String startSimulation(int noOfProducts) {
        if (checkIfSimulationCanStart()) {
            projectRepository.startQueue.setPendingProduct(noOfProducts);
            projectRepository.startQueue.processProduct();
            return "Simulation started";
        }else{
            return "Simulation can't start";
        }   
    }
}
