package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.DTO.GetDeviceDTO;
import ar.edu.utn.frc.tup.lciii.DTO.GetDeviceRestDto;
import ar.edu.utn.frc.tup.lciii.DTO.PostDeviceDTO;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final RestTemplate restTemplate;

    public GetDeviceDTO saveDevice(PostDeviceDTO deviceDTO) {
        if(validateDeviceHostname(deviceDTO.getHostname())) {
            throw new RuntimeException("Device hostname already exists");
        }
        Device device = deviceDtoToDeviceEntity(deviceDTO);
        device.setCreatedDate(LocalDateTime.now());
        deviceRepository.save(device);

        return deviceEntityToDto(device);
    }

    public List<GetDeviceDTO> getAllByType(String type) {
        List<Device> deviceList = deviceRepository.findAllByType(type);
        return deviceList.stream().map(this::deviceEntityToDto).toList();
    }

    private List<PostDeviceDTO> getAllByApi(){

        GetDeviceRestDto[] lstDevices = restTemplate.getForObject("https://67a106a15bcfff4fabe171b0.mockapi.io/api/v1/device/device", GetDeviceRestDto[].class);

        // mapear el dto a post dto
        List<PostDeviceDTO> lstPost = new ArrayList<>();
        for(GetDeviceRestDto getDeviceRestDto : lstDevices){
            PostDeviceDTO dto = new PostDeviceDTO();
            dto.setHostname(getDeviceRestDto.getHostName());
            dto.setType(getDeviceRestDto.getType());
            dto.setOs(getDeviceRestDto.getOs());
            dto.setMacAddress(getDeviceRestDto.getMacAddress());
            lstPost.add(dto);
        }

        return lstPost;
    }

    public List<GetDeviceDTO> saveHalfForApi(){
        // Mezclar la lista
        List<PostDeviceDTO> lstPost = getAllByApi();
        Collections.shuffle(lstPost);

        // Quitar la mitad
        List<PostDeviceDTO> lstPostHalf = lstPost.subList(0, lstPost.size()/2);

        List<Device> lstDevice = lstPostHalf.stream().map(this::deviceDtoToDeviceEntity).toList();

        // Guardar la mitad
        for (Device device : lstDevice) {
            device.setCreatedDate(LocalDateTime.now());
        }

        deviceRepository.saveAll(lstDevice);

        return lstDevice.stream().map(this::deviceEntityToDto).toList();
    }

    private PostDeviceDTO deviceRestDtoToDeviceDto(GetDeviceRestDto getDeviceRestDto) {
        return PostDeviceDTO.builder()
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

    private Device deviceDtoToDeviceEntity(PostDeviceDTO deviceDTO) {
        return Device.builder()
                .hostName(deviceDTO.getHostname())
                .type(deviceDTO.getType())
                .os(deviceDTO.getOs())
                .macAddress(deviceDTO.getMacAddress())
                .build();
    }

    private GetDeviceDTO deviceEntityToDto(Device device) {
        return GetDeviceDTO.builder()
                .hostname(device.getHostName())
                .type(device.getType())
                .os(device.getOs())
                .macAddress(device.getMacAddress())
                .createdDate(device.getCreatedDate().toString())
                .build();
    }

}
