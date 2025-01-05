package com.example.demo.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.CreateService;

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

    @DeleteMapping("/removeQueue/{QueueId}")
    public String removeQueue(@PathVariable("QueueId") long id) {
        addCreateService.removeQueue(id);
        return String.valueOf(id);
    }
    @DeleteMapping("/removeMachine/{machineId}")
    public String removeMAshine(@PathVariable("machineId") long id) {
        addCreateService.removeQueue(id);
        return String.valueOf(id);
    }

    @PutMapping("/editMachineInQueue")
    public ResponseEntity<?> editMachineInQueue(@RequestBody Map<String, String> body) {
        boolean res = addCreateService.editMachineInQueue(Long.parseLong(body.get("machineId")), Long.parseLong(body.get("queueId")));
        if (res) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping("/editMachineOutQueue")
    public ResponseEntity<?> editMachineOutQueue(@RequestBody Map<String, String> body) {
        boolean res = addCreateService.editMachineOutQueue(Long.parseLong(body.get("machineId")), Long.parseLong(body.get("queueId")));
        if (res) {
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

}
