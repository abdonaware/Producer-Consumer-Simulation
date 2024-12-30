package com.example.demo;

import java.util.List;
import com.example.demo.Classes.Machine;
import com.example.demo.Classes.Queue;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    private static ProjectRepository instance;
    public Queue startQueue;
    public Queue endQueue;
    public int MachineId = 0;
    public int QueueId = 0;
    public List<Machine> machines;
    public List<Queue> queues;

    private ProjectRepository() {
        // Initialize your lists here
    }

    public static synchronized ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }
}
