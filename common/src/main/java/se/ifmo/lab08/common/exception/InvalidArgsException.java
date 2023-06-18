package se.ifmo.lab08.common.exception;

public class InvalidArgsException extends RuntimeException {
    public InvalidArgsException(String message) {
        super(message);
    }

    public InvalidArgsException() {
        super("Invalid arguments");
    }
}
