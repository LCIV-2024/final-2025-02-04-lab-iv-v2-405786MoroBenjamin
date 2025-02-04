package ar.edu.utn.frc.tup.lciii.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class postTelemetryDTO {
    private String ip;
    private String hostname;
    private Double cpuUsage;
    private Double hostDiskFree;
    private String microphoneState;
    private Boolean screenCaptureAllowed;
    private Boolean audioCaptureAllowed;
    private LocalDateTime dataDate;
}
