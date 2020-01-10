package ua.com.spring.core.test.exceptions;

public class NoSuchEventException extends RuntimeException {

    public NoSuchEventException() {
        super();
    }

    public NoSuchEventException(String message) {
        super(message);
    }

    public NoSuchEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEventException(Throwable cause) {
        super(cause);
    }

    protected NoSuchEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
