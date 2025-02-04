package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.DTO.GetDeviceDTO;
import ar.edu.utn.frc.tup.lciii.DTO.PostDeviceDTO;
import ar.edu.utn.frc.tup.lciii.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<GetDeviceDTO> postDevice(@RequestBody PostDeviceDTO device){
        GetDeviceDTO dto = deviceService.saveDevice(device);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping("/device")
    public ResponseEntity<List<GetDeviceDTO>> getAllByType(@RequestParam String type){
        List<GetDeviceDTO> dto = deviceService.getAllByType(type);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/save-consumed-devices")
    public ResponseEntity<List<GetDeviceDTO>> saveHalfForApi(){
        List<GetDeviceDTO> dto = deviceService.saveHalfForApi();
        return ResponseEntity.status(200).body(dto);
    }

//    @GetMapping("/device")
//    public ResponseEntity<List<getDeviceDTO>> getAllByFilters(@RequestParam Double lowThreshold, @RequestParam Double upThreshold){
//        List<getDeviceDTO> dto = deviceService.getAllByLastTelemetryAndEntre(lowThreshold, upThreshold);
//        return ResponseEntity.status(200).body(dto);
//    }

}