package it.pliot.equipment.conf.security;

public class SecurityExceptions extends RuntimeException{
    public SecurityExceptions() {
    }

    public SecurityExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SecurityExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityExceptions(Throwable cause) {
        super(cause);
    }

    public SecurityExceptions(String message) {
        super(message);
    }
}
