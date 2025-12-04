package kafka.system.core.dto.AccountService;

import lombok.Data;

import java.util.UUID;


@Data
public class TranFailure {
    private UUID id;

    public TranFailure(){}

    public TranFailure(UUID id) {
        this.id = id;
    }
}
