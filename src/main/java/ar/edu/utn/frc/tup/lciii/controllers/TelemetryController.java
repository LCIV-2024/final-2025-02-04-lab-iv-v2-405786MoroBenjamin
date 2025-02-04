package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.DTO.GetTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.DTO.PostTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.services.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping("/telemetry")
    public ResponseEntity<GetTelemetryDTO> postDevice(@RequestBody PostTelemetryDTO device){
        GetTelemetryDTO dto = telemetryService.saveTelemetry(device);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping("/telemetry")
    public ResponseEntity<List<GetTelemetryDTO>> getDevice(@RequestParam(required = false) String hostname){
        if(hostname != null){
            return ResponseEntity.status(200).body(telemetryService.getAllTelemertryByHostname(hostname));
        }
        return ResponseEntity.status(200).body(telemetryService.getAllTelemertry());
    }

}