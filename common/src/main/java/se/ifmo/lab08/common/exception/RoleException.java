package se.ifmo.lab08.common.exception;

public class RoleException extends RuntimeException {
    public RoleException(String message) {
        super(message);
    }

    public RoleException() {
        super();
    }
}
