package kafka.system.PeopleService.persistence.model.additionalInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import kafka.system.PeopleService.persistence.model.Manager;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Deprecated
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "id")
@ToString(exclude = "manager")
@EqualsAndHashCode(exclude = "manager")
public class ManagerAdditionalInfo extends PeopleAdditionalInfo{

//    @OneToOne(mappedBy = "additionalInfo")
//    private Manager manager;
//
//    public void setManager(Manager manager) {
//        this.manager = manager;
//        if (manager != null && manager.getAdditionalInfo() != this) {
//            manager.setAdditionalInfo(this);
//        }
//    }

}
