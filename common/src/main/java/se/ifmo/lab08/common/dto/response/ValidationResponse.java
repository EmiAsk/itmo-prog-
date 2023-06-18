package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.dto.Credentials;
import se.ifmo.lab08.common.dto.StatusCode;

public record ValidationResponse(
        String message,
        StatusCode status,
        Credentials credentials
) implements Response {
    public ValidationResponse(String message) {
        this(message, StatusCode.OK);
    }

    public ValidationResponse(String message, StatusCode status) {
        this(message, status, null);
    }
}
