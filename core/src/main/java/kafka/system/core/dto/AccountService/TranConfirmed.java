package kafka.system.core.dto.AccountService;

import lombok.Data;
import java.util.UUID;

@Data
public class TranConfirmed {
    private UUID id;

    public TranConfirmed(){}

    public TranConfirmed(UUID id) {
        this.id = id;
    }
}
