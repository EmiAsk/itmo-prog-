package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.dto.Credentials;
import se.ifmo.lab08.common.dto.StatusCode;

public record CommandResponse(String message, StatusCode status, Credentials credentials) implements Response {
    public CommandResponse(String message) {
        this(message, StatusCode.OK);
    }

    public CommandResponse(String message, StatusCode status) {
        this(message, status, null);
    }
}
