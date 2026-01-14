package kafka.system.core.dto.AuthService;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SecretKeyDto {
    private String value;
    private LocalDateTime timestamp;
}
