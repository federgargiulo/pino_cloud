package it.pliot.equipment.service.ext;

public class UserAlreadyPresentException extends RuntimeException {
    public UserAlreadyPresentException() {
    }

    public UserAlreadyPresentException(String message) {
        super(message);
    }

    public UserAlreadyPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyPresentException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
