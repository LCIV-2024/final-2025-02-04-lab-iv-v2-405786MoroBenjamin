package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.DTO.getTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.DTO.postTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repositories.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelemetryService {
    private final DeviceRepository deviceRepository;
    private final TelemetryRepository telemetryRepository;

    public getTelemetryDTO saveTelemetry(postTelemetryDTO telemetryDTO) {
        if(!validateDevice(telemetryDTO.getHostname())) {
            throw new RuntimeException("Device not found");
        }
        Telemetry telemetry = telemetryDtoToEntity(telemetryDTO);
        telemetryRepository.save(telemetry);

        return dtoToTelemetryEntity(telemetry);
    }


    public List<getTelemetryDTO> getAllTelemertry() {
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        return telemetryList.stream().map(this::dtoToTelemetryEntity).toList();
    }

    public List<getTelemetryDTO> getAllTelemertryByHostname(String hostname) {
        List<Telemetry> telemetryList = telemetryRepository.findAllByDeviceHostName(hostname);
        return telemetryList.stream().map(this::dtoToTelemetryEntity).toList();
    }

    private boolean validateDevice(String hostname) {
        return deviceRepository.existsById(hostname);
    }

    private Telemetry telemetryDtoToEntity(postTelemetryDTO telemetryDTO) {
        return Telemetry.builder()
                .ip(telemetryDTO.getIp())
                .hostname(telemetryDTO.getHostname())
                .hostname(telemetryDTO.getHostname())
                .dataDate(telemetryDTO.getDataDate())
                .hostDiskFree(telemetryDTO.getHostDiskFree())
                .cpuUsage(telemetryDTO.getCpuUsage())
                .microphoneState(telemetryDTO.getMicrophoneState())
                .screenCaptureAllowed(telemetryDTO.getScreenCaptureAllowed())
                .audioCaptureAllowed(telemetryDTO.getAudioCaptureAllowed())
                .build();
    }


    private getTelemetryDTO dtoToTelemetryEntity(Telemetry telemetry) {
        return getTelemetryDTO.builder()
                .id(telemetry.getId())
                .ip(telemetry.getIp())
                .hostname(telemetry.getHostname())
                .dataDate(telemetry.getDataDate())
                .hostDiskFree(telemetry.getHostDiskFree())
                .microphoneState(telemetry.getMicrophoneState())
                .screenCaptureAllowed(telemetry.getScreenCaptureAllowed())
                .audioCaptureAllowed(telemetry.getAudioCaptureAllowed())
                .build();
    }
}
