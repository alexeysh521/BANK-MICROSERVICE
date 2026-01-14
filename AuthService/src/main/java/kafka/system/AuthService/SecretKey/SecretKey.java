package kafka.system.AuthService.SecretKey;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "secretKey")
public class SecretKey {

    @Id
    private Long id = 1L;
    private String value;
    private LocalDateTime timestamp = LocalDateTime.now();

}
