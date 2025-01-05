package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.Classes.Machine;
import com.example.demo.Classes.Queue;
import com.example.demo.DesginPattern.Snapshot;

@Repository
public class ProjectRepository {

    public Queue startQueue;
    public Queue endQueue;
    public int Id = 1;
    public List<Machine> machines;
    public List<Queue> queues;
    Snapshot snapShot = null;

    @Autowired
    private WebSocketSender webSocketSender;

    public ProjectRepository() {
        machines = new ArrayList<>();
        queues = new ArrayList<>();

    }

    public long addQueue() {
        if (startQueue == null || endQueue == null) {
            startQueue = new Queue(webSocketSender);
            endQueue = new Queue(webSocketSender);
            startQueue.setId(0);
            endQueue.setId(1000);
        }
        Queue queue = new Queue(webSocketSender);
        System.out.println("Queue created" + webSocketSender);
        queue.setId(Id++);
        queues.add(queue);
        return queue.getId();
    }

    public long addMachine() {
        if (startQueue == null || endQueue == null) {
            startQueue = new Queue(webSocketSender);
            endQueue = new Queue(webSocketSender);
            startQueue.setId(0);
            endQueue.setId(1000);
        }
        Machine machine = new Machine(webSocketSender);
        machine.setId(Id++);
        machine.setBusy(false);
        machines.add(machine);
        return machine.getId();
    }

    public Machine getMachineById(long id) {
        return machines.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    public int getQueueIndexById(long id) {
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getId() == id) {
                return i;
            }

        }

        return 0;

    }

    public int getMachineIndexById(long id) {
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getId() == id) {
                return i;
            }

        }

        return 0;

    }

    public void saveSnapShot() {
        snapShot = new Snapshot(machines, queues, startQueue, endQueue, Id);
    }

    public void RestoreSnapShot() {
        queues = snapShot.queues;
        machines = snapShot.machines;
        startQueue = snapShot.startQueue;
        endQueue = snapShot.endQueue;
        Id = snapShot.Id;
    }

    public Queue getQueueById(long id) {
        if (id == 0) {
            return startQueue;
        }
        if (id == 1000) {
            return endQueue;
        }
        return queues.stream().filter(q -> q.getId() == id).findFirst().orElse(null);
    }

    public void sendToFrontend(Map<String, String> message) {
        webSocketSender.sendMessage("/topic/messages", message);
    }
}
