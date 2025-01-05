package com.example.demo.DesginPattern;

import java.util.List;

import com.example.demo.Classes.Machine;
import com.example.demo.Classes.Queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Snapshot {
    public Queue startQueue;
    public Queue endQueue;
    public int Id = 1;
    public List<Machine> machines;
    public List<Queue> queues;

    public Snapshot(List<Machine> machines, List<Queue> queues, Queue startQueue, Queue endQueue, int Id) {
        this.machines = machines;
        this.queues = queues;   
        this.startQueue = startQueue;
        this.endQueue = endQueue;
        this.Id = Id;
    }

}
