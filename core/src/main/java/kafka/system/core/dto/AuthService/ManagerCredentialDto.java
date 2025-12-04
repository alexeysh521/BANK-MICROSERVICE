package kafka.system.core.dto.AuthService;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ManagerCredentialDto {
    private UUID id;
    private LocalDateTime timeStamp;
}
