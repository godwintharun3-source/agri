package com.agriloop360.controller;

import com.agriloop360.dto.SimulationControlDto;
import com.agriloop360.service.SensorSimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
public class SensorSimulationController {

    private final SensorSimulationService simulationService;

    public SensorSimulationController(SensorSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping("/status")
    public ResponseEntity<SimulationControlDto> getStatus() {
        return ResponseEntity.ok(SimulationControlDto.builder()
                .active(simulationService.isSimulationActive())
                .intervalSeconds(10)
                .message(simulationService.isSimulationActive() ? "Simulation active" : "Simulation stopped")
                .build());
    }

    @PostMapping("/start")
    public ResponseEntity<SimulationControlDto> start() {
        simulationService.startSimulation();
        return ResponseEntity.ok(SimulationControlDto.builder()
                .active(true)
                .intervalSeconds(10)
                .message("Sensor simulation started successfully")
                .build());
    }

    @PostMapping("/stop")
    public ResponseEntity<SimulationControlDto> stop() {
        simulationService.stopSimulation();
        return ResponseEntity.ok(SimulationControlDto.builder()
                .active(false)
                .intervalSeconds(10)
                .message("Sensor simulation stopped successfully")
                .build());
    }

    @PostMapping("/tick")
    public ResponseEntity<SimulationControlDto> triggerTick() {
        simulationService.triggerTick();
        return ResponseEntity.ok(SimulationControlDto.builder()
                .active(simulationService.isSimulationActive())
                .intervalSeconds(10)
                .message("New sensor reading tick generated and saved to database")
                .build());
    }
}
