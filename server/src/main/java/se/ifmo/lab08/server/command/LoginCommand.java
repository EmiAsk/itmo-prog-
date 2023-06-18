package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.Credentials;
import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

public class LoginCommand extends Command implements Unauthorized {

    private static final Class<?>[] ARGS = new Class<?>[]{String.class, String.class};

    public LoginCommand(CommandManager invoker) {
        super("login {username} {password}", "войти", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);

        var username = request.args()[0];
        var password = request.args()[1];
        var user = invoker.getAuthManager().authorize(username, password);
        var credentials = new Credentials(username, password, user.getRole());
        return new CommandResponse("Logged in successfully", StatusCode.OK, credentials);
    }

    @Override
    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
