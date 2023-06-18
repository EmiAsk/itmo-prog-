package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;

public class RemoveByIdCommand extends Command {

    private static final Class<?>[] ARGS = new Class<?>[]{Long.class};

    public RemoveByIdCommand(CommandManager invoker) {
        super("remove_by_id {id}", "удалить элемент из коллекции по его id", invoker);
    }

    @Override
    public void validateArgs(Request request) throws InvalidArgsException {
        super.validateArgs(request);
        long id = Long.parseLong(request.args()[0]);
        if (invoker.getCollection().get(id) == null) {
            throw new InvalidArgsException("Flat with specified ID doesn't exist");
        }
        var user = getUserByRequest(request);
        if (!invoker.getCollection().get(id).getOwner().getUsername().equals(user.getUsername())) {
            throw new InvalidArgsException("You can't remove flats you don't own");
        }
    }

    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);

        long flatId = Long.parseLong(request.args()[0]);

        flatRepository.deleteById(flatId);
        invoker.getCollection().removeById(flatId);

        return new CommandResponse("Flat (ID %s) removed successfully.\n".formatted(flatId), StatusCode.OK, request.credentials());
    }

    @Override
    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
