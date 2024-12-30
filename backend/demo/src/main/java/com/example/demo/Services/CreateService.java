package com.example.demo.Services;

import com.example.demo.ProjectRepository;
import com.example.demo.Classes.Machine;
import com.example.demo.Classes.Queue;

public class CreateService {
    private ProjectRepository repository = ProjectRepository.getInstance();

    public long addMachine(String entity) {
        Machine machine = new Machine();
        machine.setId(repository.MachineId++);
        repository.machines.add(machine);
        return machine.getId();
    }

    public void removeMachine(long id) {
        Machine machine = repository.machines.get((int) id);
        machine.getInQueues().forEach(queue -> queue.removeMashine(machine));
        machine.getOutQueues().forEach(queue -> queue.removeOutMashine(machine));
        repository.machines.remove((int) id);
    }

    public long addQueue(String entity) {
        Queue queue = new Queue();
        queue.setPendingProduct(0);
        queue.setId(repository.QueueId++);

        repository.queues.add(queue);
        return queue.getPendingProduct();
    }

    public void removeQueue(long id) {
        Queue queue = repository.queues.get((int) id);
        queue.getInMashines().forEach(machine -> machine.removeInQueue(queue));
        queue.getOutMashines().forEach(machine -> machine.removeOutQueue(queue));
        repository.queues.remove((int) id);
    }

    public void editMachineInQueue(long machineId, long queueId) {
        Machine machine = repository.machines.get((int) machineId);
        Queue queue = repository.queues.get((int) queueId);
        queue.addMashine(machine);
        machine.addInQueue(queue);
    }

    public void editMachineOutQueue(long machineId, long queueId) {
        Machine machine = repository.machines.get((int) machineId);
        Queue queue = repository.queues.get((int) queueId);
        queue.addOutMashine(machine);
        machine.addOutQueue(queue);
    }

}