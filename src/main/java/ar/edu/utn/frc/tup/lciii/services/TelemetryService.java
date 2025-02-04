package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.DTO.GetTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.DTO.PostTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repositories.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelemetryService {
    private final DeviceRepository deviceRepository;
    private final TelemetryRepository telemetryRepository;

    public GetTelemetryDTO saveTelemetry(PostTelemetryDTO telemetryDTO) {
        if(!validateDevice(telemetryDTO.getHostname())) {
            throw new RuntimeException("Device not found");
        }
        Telemetry telemetry = telemetryDtoToEntity(telemetryDTO);
        telemetryRepository.save(telemetry);

        return dtoToTelemetryEntity(telemetry);
    }


    public List<GetTelemetryDTO> getAllTelemertry() {
        List<Telemetry> telemetryList = telemetryRepository.findAll();
        return telemetryList.stream().map(this::dtoToTelemetryEntity).toList();
    }

    public List<GetTelemetryDTO> getAllTelemertryByHostname(String hostname) {
        List<Telemetry> telemetryList = telemetryRepository.findAllByDeviceHostName(hostname);
        return telemetryList.stream().map(this::dtoToTelemetryEntity).toList();
    }

    public boolean validateDevice(String hostname) {
        return deviceRepository.existsById(hostname);
    }

    public Telemetry telemetryDtoToEntity(PostTelemetryDTO telemetryDTO) {
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


    public GetTelemetryDTO dtoToTelemetryEntity(Telemetry telemetry) {
        return GetTelemetryDTO.builder()
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
