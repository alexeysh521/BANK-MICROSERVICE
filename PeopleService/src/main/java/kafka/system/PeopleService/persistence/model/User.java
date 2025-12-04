package kafka.system.PeopleService.persistence.model;

import jakarta.persistence.*;
import kafka.system.core.enums.Genders;
import kafka.system.core.enums.RolesType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private RolesType role = RolesType.USER;

    private String firstName;
    private String overageName;
    private String lastName;

    private String phone;
    private String passportNumber;
    private String passportSerial;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    private LocalDateTime dateOfBirth;
    private LocalDateTime timeStamp;

    public User() {}

    public User(UUID id, LocalDateTime timeStamp) {
        this.id = id;
        this.timeStamp = timeStamp;
    }

    public User(String firstName, String overageName, String lastName, String phone, String passportNumber, String passportSerial, Genders gender, LocalDateTime dateOfBirth) {
        this.firstName = firstName;
        this.overageName = overageName;
        this.lastName = lastName;
        this.phone = phone;
        this.passportNumber = passportNumber;
        this.passportSerial = passportSerial;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
