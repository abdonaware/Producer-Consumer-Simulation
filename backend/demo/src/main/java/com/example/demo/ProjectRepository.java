package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.Classes.Machine;
import com.example.demo.Classes.Queue;

@Repository
public class ProjectRepository {
    private static ProjectRepository instance;
    public Queue startQueue;
    public Queue endQueue;
    public int Id = 1;
    public List<Machine> machines;
    public List<Queue> queues;

    private ProjectRepository() {
        // Initialize your lists here
        machines = new ArrayList<>();
        queues = new ArrayList<>();
        startQueue = new Queue();
        endQueue = new Queue();
        startQueue.setId(0);
        endQueue.setId(1000);
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
    public Machine getMachineById(long id) {
        for (int i = 0; i < machines.size(); i++) {
            if (machines.get(i).getId() == id) {
                return machines.get(i);
            }
        }
        return null;
    }
    public Queue getQueueById(long id) {
        if (id == 0) {
            return startQueue;
        }
        if (id == 1000) {
            return endQueue;
        }
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getId() == id) {
                return queues.get(i);
            }
        }
        return null;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }
}
