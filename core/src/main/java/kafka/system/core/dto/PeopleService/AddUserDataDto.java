package kafka.system.core.dto.PeopleService;

import kafka.system.core.enums.Genders;
import lombok.Data;
import java.util.UUID;


@Data
public class AddUserDataDto {
    private UUID id;
    private String firstName;
    private String overageName;
    private String lastName;
    private String phone;
    private String passportNumber;
    private String passportSerial;
    private Genders gender;
    private String dateOfBirth;
}
