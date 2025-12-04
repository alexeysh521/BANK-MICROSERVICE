package kafka.system.core.dto.AuthService;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotNull(message = "Поле не может быть пустым") private String username;
    @NotNull(message = "Поле не может быть пустым") private String password;
    @NotNull(message = "Поле не может быть пустым") private String email;
}
