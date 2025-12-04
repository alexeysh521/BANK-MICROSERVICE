package kafka.system.PeopleService.persistence.model.additionalInfo;

import jakarta.persistence.*;
import kafka.system.PeopleService.persistence.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Deprecated
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "id")
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class UserAdditionalInfo extends PeopleAdditionalInfo {


}
