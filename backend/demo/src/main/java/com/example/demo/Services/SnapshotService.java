package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Classes.Machine;
import com.example.demo.ProjectRepository;
@Service
public class SnapshotService {

    @Autowired
    private ProjectRepository projectRepository;



    public void reloadSnapShot() {

        for (Machine m : projectRepository.machines) {
            m.exitProcess();
        }
        projectRepository.RestoreSnapShot();
    }

}
