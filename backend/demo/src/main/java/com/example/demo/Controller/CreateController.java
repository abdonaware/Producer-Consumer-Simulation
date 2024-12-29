package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.Services.CreateService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CreateController {

    @Autowired
    private CreateService addCreateService;

    @PostMapping("/addMachine")
    public long addMachine(@RequestBody String entity) {
        return addCreateService.addMachine(entity);
    }

    @DeleteMapping("/removeMachine")
    public String removeMachine(@RequestBody String entity) {
        addCreateService.removeMachine(Long.parseLong(entity));
        return entity;
    }

    @PostMapping("/addQueue")
    public long addQueue(@RequestBody String entity) {
        return addCreateService.addQueue(entity);
    }

    @DeleteMapping("/removeQueue")
    public String removeQueue(@RequestBody String entity) {
        addCreateService.removeMachine(Long.parseLong(entity));
        return entity;
    }

    @PutMapping("/editMachineInQueue/{machineId}/{queueId}")
    public String editMachineInQueue(@PathVariable String machineId, @PathVariable String queueId) {
        addCreateService.editMachineInQueue(Long.parseLong(machineId), Long.parseLong(queueId));
        return machineId + " " + queueId;
    }

    @PutMapping("/editMachineOutQueue/{machineId}/{queueId}")
    public String editMachineOutQueue(@PathVariable String machineId, @PathVariable String queueId) {
        addCreateService.editMachineInQueue(Long.parseLong(machineId), Long.parseLong(queueId));
        return machineId + " " + queueId;
    }

}
