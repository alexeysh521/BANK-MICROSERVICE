package kafka.system.AuthService.persistence.model;

import jakarta.persistence.*;
import kafka.system.core.enums.RolesType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private RolesType role = RolesType.USER;

    private LocalDateTime timeStamp = LocalDateTime.now();

    public UserCredential() {}

    public UserCredential(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
