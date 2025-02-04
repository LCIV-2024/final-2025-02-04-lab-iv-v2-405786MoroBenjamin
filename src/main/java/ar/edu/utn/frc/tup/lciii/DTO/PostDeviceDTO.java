package ar.edu.utn.frc.tup.lciii.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDeviceDTO {
    private String hostname;
    private String type;
    private String os;
    private String macAddress;
}
