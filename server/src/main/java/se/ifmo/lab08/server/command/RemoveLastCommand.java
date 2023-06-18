package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;

public class RemoveLastCommand extends Command {
    public RemoveLastCommand(CommandManager invoker) {
        super("remove_last", "удалить последний элемент из коллекции", invoker);
    }

    @Override
    public void validateArgs(Request request) throws InvalidArgsException {
        super.validateArgs(request);
        var flat = invoker.getCollection().last();
        if (flat == null) {
            throw new InvalidArgsException("Collection is empty");
        }
        var user = getUserByRequest(request);
        if (!user.getUsername().equals(flat.getOwner().getUsername())) {
            throw new InvalidArgsException("You can't remove flats you don't own");
        }
    }


    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);

        var user = getUserByRequest(request);
        var flat = invoker.getCollection().last();

        if (!flat.getOwner().getUsername().equals(user.getUsername())) {
            return new CommandResponse("You can't remove flats you don't own", StatusCode.ERROR, request.credentials());
        }

        flatRepository.deleteById(flat.getId());
        invoker.getCollection().removeById(flat.getId());

        return new CommandResponse("Last flat removed successfully.", StatusCode.OK, request.credentials());
    }
}
