package ua.com.spring.core.test.exceptions;

public class AuditoriumNotFound extends RuntimeException {
    public AuditoriumNotFound() {
        super();
    }

    public AuditoriumNotFound(String message) {
        super(message);
    }

    public AuditoriumNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditoriumNotFound(Throwable cause) {
        super(cause);
    }

    protected AuditoriumNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
