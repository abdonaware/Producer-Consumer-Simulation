package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Classes.Machine;
import com.example.demo.Classes.Queue;
import com.example.demo.ProjectRepository;

@Service
public class CreateService {
    @Autowired
    private ProjectRepository repository ;

    public long addMachine(String entity) {

        long id = repository.addMachine();
        return id;
    }

    public void removeMachine(long id) {
        Machine machine = repository.machines.get((int) id);
        machine.getInQueues().forEach(queue -> queue.removeMashine(machine));
        machine.getOutQueues().forEach(queue -> queue.removeOutMashine(machine));
        repository.machines.remove((int) id);
    }

    public long addQueue(String entity) {

        long id = repository.addQueue();
        return id;
    }

    public void removeQueue(long id) {
        Queue queue = repository.queues.get((int) id);
        queue.getInMashines().forEach(machine -> machine.removeInQueue(queue));
        queue.getOutMashines().forEach(machine -> machine.removeOutQueue(queue));
        repository.queues.remove((int) id);
    }

    public Boolean editMachineInQueue(long machineId, long queueId) {
        Machine machine = repository.getMachineById(machineId);
        System.out.println("machine :" + machine);
        if (machine == null) {
            System.out.println("Machine not found");
            return false;
        }
        Queue queue = repository.getQueueById(queueId);
        if (queue == null) {
            System.out.println("Queue not found");
            return false;
        }
        System.out.println("queue :" + queue);
        queue.addMashine(machine);
        machine.addInQueue(queue);
        return true;
    }

    public boolean editMachineOutQueue(long machineId, long queueId) {
        Machine machine = repository.getMachineById(machineId);
        System.out.println("machine :" + machine);
        if (machine == null) {
            System.out.println("Machine not found");
            return false;
        }
        Queue queue = repository.getQueueById(queueId);
        if (queue == null) {
            System.out.println("Queue not found");
            return false;
        }
        queue.addOutMashine(machine);
        machine.addOutQueue(queue);
        return true;
    }

}
