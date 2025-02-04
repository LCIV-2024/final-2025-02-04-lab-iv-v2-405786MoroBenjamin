package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DeviceService deviceService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDevice() {
    }

    @Test
    void getAllByType() {
    }

    @Test
    void saveHalfForApi() {
    }

    @Test
    void testSaveDevice() {
    }

    @Test
    void testGetAllByType() {
    }

    @Test
    void getAllByApi() {
    }

    @Test
    void testSaveHalfForApi() {
    }

    @Test
    void deviceRestDtoToDeviceDto() {
    }

    @Test
    void validateDeviceHostname() {
        when(deviceService.validateDeviceHostname("hostname")).thenReturn(true);
    }

    @Test
    void deviceDtoToDeviceEntity() {
    }

    @Test
    void deviceEntityToDto() {
    }
}