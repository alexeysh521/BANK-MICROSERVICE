package kafka.system.core.dto.PeopleService;

import kafka.system.core.enums.Genders;
import kafka.system.core.enums.RolesType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ViewDataUser {

    private RolesType role;

    private String firstName;
    private String overageName;
    private String lastName;

    private String phone;
    private String passportNumber;
    private String passportSerial;

    private Genders gender;

    private LocalDateTime dateOfBirth;
    private LocalDateTime timeStamp;
}
