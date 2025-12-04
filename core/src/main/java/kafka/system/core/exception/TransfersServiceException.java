package kafka.system.core.exception;

public class TransfersServiceException extends RuntimeException {
    public TransfersServiceException(Throwable cause) {
        super(cause);
    }

    public TransfersServiceException(String message) {
        super(message);
    }

}
