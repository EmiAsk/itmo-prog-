package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CollectionManager;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;

public class ChangeRoleCommand extends Command {
    private static final Class<?>[] ARGS = new Class<?>[]{Integer.class, Role.class};

    public ChangeRoleCommand(CommandManager invoker) {
        super("change_role {user_id} {role}", "поменять роль польвателя по id", invoker);
    }

    @Override
    public void validateArgs(Request request) throws InvalidArgsException {
        super.validateArgs(request);
        int id = Integer.parseInt(request.args()[0]);
        userRepository.findById(id).orElseThrow(() -> new InvalidArgsException("User not found"));
    }

    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);

        int id = Integer.parseInt(request.args()[0]);
        Role role = Role.valueOf(request.args()[1]);

        var user = userRepository.findById(id).orElseThrow(() -> new InvalidArgsException("User not found"));
        user.setRole(role);
        userRepository.save(user);

        return new CommandResponse("User role has been changed", StatusCode.OK, request.credentials());
    }

    @Override
    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
