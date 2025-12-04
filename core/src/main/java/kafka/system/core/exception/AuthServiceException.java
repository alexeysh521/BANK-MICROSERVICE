package kafka.system.core.exception;

public class AuthServiceException extends RuntimeException {
    public AuthServiceException(String message) {
        super(message);
    }

    public AuthServiceException(Throwable cause) {
        super(cause);
    }

    public AuthServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
