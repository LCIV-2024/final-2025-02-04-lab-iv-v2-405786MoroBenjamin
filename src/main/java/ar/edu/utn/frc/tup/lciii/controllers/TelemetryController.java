package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.DTO.getDeviceDTO;
import ar.edu.utn.frc.tup.lciii.DTO.getTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.DTO.postDeviceDTO;
import ar.edu.utn.frc.tup.lciii.DTO.postTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.services.TelemetryService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping("/telemetry")
    public ResponseEntity<getTelemetryDTO> postDevice(@RequestBody postTelemetryDTO device){
        getTelemetryDTO dto = telemetryService.saveTelemetry(device);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping("/telemetry")
    public ResponseEntity<List<getTelemetryDTO>> getDevice(@RequestParam(required = false) String hostname){
        if(hostname != null){
            return ResponseEntity.status(200).body(telemetryService.getAllTelemertryByHostname(hostname));
        }
        return ResponseEntity.status(200).body(telemetryService.getAllTelemertry());
    }

}