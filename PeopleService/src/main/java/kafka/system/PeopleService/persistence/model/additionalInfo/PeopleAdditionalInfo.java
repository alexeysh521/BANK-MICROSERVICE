package kafka.system.PeopleService.persistence.model.additionalInfo;

import jakarta.persistence.*;
import kafka.system.core.enums.Genders;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Deprecated
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "people_additional_info")
public abstract class PeopleAdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String overageName;
    private String lastName;

    private String phone;
    private String passportNumber;
    private String passportSerial;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    private LocalDateTime dateOfBirth;
}
