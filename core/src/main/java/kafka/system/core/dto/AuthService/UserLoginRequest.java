package kafka.system.core.dto.AuthService;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotNull(message = "Поле не может быть пустым") private String password;
    @NotNull(message = "Поле не может быть пустым") private String email;
}
