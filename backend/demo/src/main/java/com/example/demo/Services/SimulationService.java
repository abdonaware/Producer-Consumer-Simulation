package com.example.demo.Services;

import org.springframework.stereotype.Service;
import com.example.demo.ProjectRepository;

@Service
public class SimulationService {
    private ProjectRepository projectRepository = ProjectRepository.getInstance();

}
    

