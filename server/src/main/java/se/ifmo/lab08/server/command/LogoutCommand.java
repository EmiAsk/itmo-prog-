package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

public class LogoutCommand extends Command implements Unauthorized {

    public LogoutCommand(CommandManager invoker) {
        super("logout", "выйти", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);

        return new CommandResponse("Logged out successfully");
    }
}
