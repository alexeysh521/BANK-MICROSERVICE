package kafka.system.PeopleService.persistence.model.additionalInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import kafka.system.PeopleService.persistence.model.Admin;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Deprecated
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "id")
@ToString(exclude = "admin")
@EqualsAndHashCode(exclude = "admin")
public class AdminAdditionalInfo extends PeopleAdditionalInfo{

//    @OneToOne(mappedBy = "additionalInfo")
//    private Admin admin;
//
//    public void setAdmin(Admin admin){
//        this.admin = admin;
//        if(admin != null && admin.getAdditionalInfo() != this){
//            admin.setAdditionalInfo(this);
//        }
//    }

}
