package ar.edu.utn.frc.tup.lciii.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetDeviceRestDto {
    private String id;
    private String hostName;
    private String type;
    private String os;
    private String macAddress;
    private String createdDate;
}
