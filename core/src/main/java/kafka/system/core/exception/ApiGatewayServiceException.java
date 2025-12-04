package kafka.system.core.exception;

public class ApiGatewayServiceException extends RuntimeException {
    public ApiGatewayServiceException(String message) {
        super(message);
    }

    public ApiGatewayServiceException(Throwable cause) {
        super(cause);
    }

    public ApiGatewayServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
