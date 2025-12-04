package kafka.system.core.dto.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Deprecated
@Data
public class LoginOrRegisterRequest {

    private String username;
    private String password;

    @NotNull(message = "Email must be not null")
    @Email(message = "Write a valid email")
    private String email;
}
