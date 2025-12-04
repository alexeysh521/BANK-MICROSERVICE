package kafka.system.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Deprecated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferFailed{
    private UUID transactionId;
    private String message;
    private LocalDateTime time;
}
