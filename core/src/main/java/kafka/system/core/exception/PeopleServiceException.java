package kafka.system.core.exception;

public class PeopleServiceException extends RuntimeException {
    public PeopleServiceException(String message) {
        super(message);
    }

    public PeopleServiceException(Throwable cause) {
        super(cause);
    }

    public PeopleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
