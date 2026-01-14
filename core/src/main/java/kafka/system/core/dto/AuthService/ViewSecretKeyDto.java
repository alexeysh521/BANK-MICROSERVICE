package kafka.system.core.dto.AuthService;

import lombok.Data;

@Data
public class ViewSecretKeyDto {
    private String email;
    private String password;
}
