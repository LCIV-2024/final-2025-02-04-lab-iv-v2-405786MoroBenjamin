package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.DTO.getDeviceDTO;
import ar.edu.utn.frc.tup.lciii.DTO.postDeviceDTO;
import ar.edu.utn.frc.tup.lciii.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<getDeviceDTO> postDevice(@RequestBody postDeviceDTO device){
        getDeviceDTO dto = deviceService.saveDevice(device);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping("/device")
    public ResponseEntity<List<getDeviceDTO>> getAllByType(@RequestParam String type){
        List<getDeviceDTO> dto = deviceService.getAllByType(type);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/save-consumed-devices")
    public ResponseEntity<List<getDeviceDTO>> saveHalfForApi(){
        List<getDeviceDTO> dto = deviceService.saveHalfForApi();
        return ResponseEntity.status(200).body(dto);
    }

//    @GetMapping("/device")
//    public ResponseEntity<List<getDeviceDTO>> getAllByFilters(@RequestParam Double lowThreshold, @RequestParam Double upThreshold){
//        List<getDeviceDTO> dto = deviceService.getAllByLastTelemetryAndEntre(lowThreshold, upThreshold);
//        return ResponseEntity.status(200).body(dto);
//    }

}