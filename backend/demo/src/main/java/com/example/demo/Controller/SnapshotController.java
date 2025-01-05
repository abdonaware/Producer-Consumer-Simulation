package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.Services.SnapshotService;

@Controller
public class SnapshotController {

    @Autowired
    private SnapshotService snapshotService;

    @PutMapping("/restart")
    public String snapshotRestart() {
        snapshotService.reloadSnapShot();

        return "snapshot reload success";

    }

}
