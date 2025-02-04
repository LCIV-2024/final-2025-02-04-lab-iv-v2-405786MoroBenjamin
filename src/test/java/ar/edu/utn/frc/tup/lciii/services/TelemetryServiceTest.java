package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.DTO.GetTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.DTO.PostTelemetryDTO;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repositories.TelemetryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TelemetryServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private TelemetryRepository telemetryRepository;

    @InjectMocks
    private TelemetryService telemetryService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void saveTelemetryGood() {
        PostTelemetryDTO postTelemetryDTO = new PostTelemetryDTO();
        postTelemetryDTO.setHostname("hostname");
        postTelemetryDTO.setIp("ip");
        postTelemetryDTO.setCpuUsage(1.0);
        postTelemetryDTO.setHostDiskFree(1.0);
        postTelemetryDTO.setMicrophoneState("microphoneState");
        postTelemetryDTO.setScreenCaptureAllowed(true);
        postTelemetryDTO.setAudioCaptureAllowed(true);
        postTelemetryDTO.setDataDate(null);

        GetTelemetryDTO getTelemetryDTO = new GetTelemetryDTO();
        getTelemetryDTO.setHostname("hostname");
        getTelemetryDTO.setIp("ip");
        getTelemetryDTO.setHostDiskFree(1.0);
        getTelemetryDTO.setMicrophoneState("microphoneState");
        getTelemetryDTO.setScreenCaptureAllowed(true);
        getTelemetryDTO.setAudioCaptureAllowed(true);
        getTelemetryDTO.setDataDate(null);

        when(deviceRepository.existsById(postTelemetryDTO.getHostname())).thenReturn(true);

        when(telemetryRepository.save(telemetryService.telemetryDtoToEntity(postTelemetryDTO))).thenReturn(null);

        GetTelemetryDTO telemetryDTO = telemetryService.saveTelemetry(postTelemetryDTO);

        assertEquals(getTelemetryDTO, telemetryDTO);

    }

    @Test
    void getAllTelemertry() {
        GetTelemetryDTO getTelemetryDTO = new GetTelemetryDTO();
        getTelemetryDTO.setHostname("hostname");
        getTelemetryDTO.setIp("ip");
        getTelemetryDTO.setHostDiskFree(1.0);
        getTelemetryDTO.setMicrophoneState("microphoneState");
        getTelemetryDTO.setScreenCaptureAllowed(true);
        getTelemetryDTO.setAudioCaptureAllowed(true);
        getTelemetryDTO.setDataDate(null);

        when(telemetryRepository.findAll()).thenReturn((List<Telemetry>) getTelemetryDTO);

        List<GetTelemetryDTO> telemetryDTOList = telemetryService.getAllTelemertry();

        assertEquals(List.of(getTelemetryDTO), telemetryDTOList);
    }

    @Test
    void getAllTelemertryByHostname() {
    }

    @Test
    void validateDevice() {
        when(deviceRepository.existsById("hostname")).thenReturn(true);

        assertTrue(telemetryService.validateDevice("hostname"));
    }

    @Test
    void validateDeviceFalse() {
        when(deviceRepository.existsById("hostname")).thenReturn(false);

        assertTrue(!telemetryService.validateDevice("hostname"));
    }

    @Test
    void telemetryDtoToEntity() {
        PostTelemetryDTO postTelemetryDTO = new PostTelemetryDTO();
        postTelemetryDTO.setHostname("hostname");
        postTelemetryDTO.setIp("ip");
        postTelemetryDTO.setCpuUsage(1.0);
        postTelemetryDTO.setHostDiskFree(1.0);
        postTelemetryDTO.setMicrophoneState("microphoneState");
        postTelemetryDTO.setScreenCaptureAllowed(true);
        postTelemetryDTO.setAudioCaptureAllowed(true);
        postTelemetryDTO.setDataDate(null);

        GetTelemetryDTO getTelemetryDTO = new GetTelemetryDTO();
        getTelemetryDTO.setHostname("hostname");
        getTelemetryDTO.setIp("ip");
        getTelemetryDTO.setHostDiskFree(1.0);
        getTelemetryDTO.setMicrophoneState("microphoneState");
        getTelemetryDTO.setScreenCaptureAllowed(true);
        getTelemetryDTO.setAudioCaptureAllowed(true);
        getTelemetryDTO.setDataDate(null);

        Telemetry telemetry = telemetryService.telemetryDtoToEntity(postTelemetryDTO);

        assertEquals(getTelemetryDTO.getHostname(), telemetry.getHostname());
    }

    @Test
    void dtoToTelemetryEntity() {
        PostTelemetryDTO postTelemetryDTO = new PostTelemetryDTO();
        postTelemetryDTO.setHostname("hostname");
        postTelemetryDTO.setIp("ip");
        postTelemetryDTO.setCpuUsage(1.0);
        postTelemetryDTO.setHostDiskFree(1.0);
        postTelemetryDTO.setMicrophoneState("microphoneState");
        postTelemetryDTO.setScreenCaptureAllowed(true);
        postTelemetryDTO.setAudioCaptureAllowed(true);
        postTelemetryDTO.setDataDate(null);

        GetTelemetryDTO getTelemetryDTO = new GetTelemetryDTO();
        getTelemetryDTO.setHostname("hostname");
        getTelemetryDTO.setIp("ip");
        getTelemetryDTO.setHostDiskFree(1.0);
        getTelemetryDTO.setMicrophoneState("microphoneState");
        getTelemetryDTO.setScreenCaptureAllowed(true);
        getTelemetryDTO.setAudioCaptureAllowed(true);
        getTelemetryDTO.setDataDate(null);

        GetTelemetryDTO dto = telemetryService.dtoToTelemetryEntity(telemetryService.telemetryDtoToEntity(postTelemetryDTO));

        assertEquals(getTelemetryDTO.getHostname(), dto.getHostname());
    }
}