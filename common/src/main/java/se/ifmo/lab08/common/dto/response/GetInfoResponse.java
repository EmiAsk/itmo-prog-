package se.ifmo.lab08.common.dto.response;

import se.ifmo.lab08.common.dto.CommandDTO;
import se.ifmo.lab08.common.dto.Credentials;
import se.ifmo.lab08.common.dto.StatusCode;

import java.util.List;

public record GetInfoResponse(
        List<CommandDTO> commands,
        Credentials credentials,
        StatusCode status
) implements Response {
    public GetInfoResponse(List<CommandDTO> commands) {
        this(commands, null, StatusCode.OK);
    }

    public GetInfoResponse(List<CommandDTO> commands, StatusCode status) {
        this(commands, null, status);
    }
}
