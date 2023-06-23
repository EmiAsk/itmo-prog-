package se.ifmo.lab08.common.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super("Command not found. %s".formatted(message));
    }

    public AuthorizationException() {
        this("");
    }
}
