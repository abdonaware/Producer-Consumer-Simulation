package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.SnapshotService;

@RestController
public class SnapshotController {

    @Autowired
    private SnapshotService snapshotService;

    @GetMapping("/restart")
    public String snapshotRestart() {

        snapshotService.reloadSnapShot();

        return "snapshot reload success";

    }

}
