package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.DTO.getDeviceDTO;
import ar.edu.utn.frc.tup.lciii.DTO.getDeviceRestDto;
import ar.edu.utn.frc.tup.lciii.DTO.postDeviceDTO;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repositories.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final RestTemplate restTemplate;

    public getDeviceDTO saveDevice(postDeviceDTO deviceDTO) {
        if(validateDeviceHostname(deviceDTO.getHostname())) {
            throw new RuntimeException("Device hostname already exists");
        }
        Device device = deviceDtoToDeviceEntity(deviceDTO);
        device.setCreatedDate(LocalDateTime.now());
        deviceRepository.save(device);

        return deviceEntityToDto(device);
    }

    public List<getDeviceDTO> getAllByType(String type) {
        List<Device> deviceList = deviceRepository.findAllByType(type);
        return deviceList.stream().map(this::deviceEntityToDto).toList();
    }

    private List<postDeviceDTO> getAllByApi(){

        getDeviceRestDto[] lstDevices = restTemplate.getForObject("https://67a106a15bcfff4fabe171b0.mockapi.io/api/v1/device/device", getDeviceRestDto[].class);

        // mapear el dto a post dto
        List<postDeviceDTO> lstPost = new ArrayList<>();
        for(getDeviceRestDto getDeviceRestDto : lstDevices){
            postDeviceDTO dto = new postDeviceDTO();
            dto.setHostname(getDeviceRestDto.getHostName());
            dto.setType(getDeviceRestDto.getType());
            dto.setOs(getDeviceRestDto.getOs());
            dto.setMacAddress(getDeviceRestDto.getMacAddress());
            lstPost.add(dto);
        }

        return lstPost;
    }

    public List<getDeviceDTO> saveHalfForApi(){
        // Mezclar la lista
        List<postDeviceDTO> lstPost = getAllByApi();
        Collections.shuffle(lstPost);

        // Quitar la mitad
        List<postDeviceDTO> lstPostHalf = lstPost.subList(0, lstPost.size()/2);

        List<Device> lstDevice = lstPostHalf.stream().map(this::deviceDtoToDeviceEntity).toList();

        // Guardar la mitad
        for (Device device : lstDevice) {
            device.setCreatedDate(LocalDateTime.now());
        }

        deviceRepository.saveAll(lstDevice);

        return lstDevice.stream().map(this::deviceEntityToDto).toList();
    }

    private postDeviceDTO deviceRestDtoToDeviceDto(getDeviceRestDto getDeviceRestDto) {
        return postDeviceDTO.builder()
                .hostname(getDeviceRestDto.getHostName())
                .type(getDeviceRestDto.getType())
                .os(getDeviceRestDto.getOs())
                .macAddress(getDeviceRestDto.getMacAddress())
                .build();
    }

//    public List<getDeviceDTO> getAllByLastTelemetryAndEntre(Double low, Double high){
//        // Buscar la ultma telemetry
//        Telemetry telemetry = telemetryRepository.findFirstByOrderByDataDateDesc();
//
//        List<Device> ltsDevice = deviceRepository.findByTelemetry(telemetry);
//
//        // Filtrar para que cpuUsage este entre low y high
//        ltsDevice.removeIf(device -> device.getTelemetry().getCpuUsage() < low || device.getTelemetry().getCpuUsage() > high);
//
//        return ltsDevice.stream().map(this::deviceEntityToDto).toList();
//    }

    private boolean validateDeviceHostname(String hostname) {
        return deviceRepository.existsById(hostname);
    }

    private Device deviceDtoToDeviceEntity(postDeviceDTO deviceDTO) {
        return Device.builder()
                .hostName(deviceDTO.getHostname())
                .type(deviceDTO.getType())
                .os(deviceDTO.getOs())
                .macAddress(deviceDTO.getMacAddress())
                .build();
    }

    private getDeviceDTO deviceEntityToDto(Device device) {
        return getDeviceDTO.builder()
                .hostname(device.getHostName())
                .type(device.getType())
                .os(device.getOs())
                .macAddress(device.getMacAddress())
                .createdDate(device.getCreatedDate().toString())
                .build();
    }

}
