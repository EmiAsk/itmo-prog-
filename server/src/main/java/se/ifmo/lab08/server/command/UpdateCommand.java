package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.dto.response.UpdateModelResponse;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;

public class UpdateCommand extends Command {

    private static final Class<?>[] ARGS = new Class<?>[]{Long.class};

    public UpdateCommand(CommandManager invoker) {
        super("update {id} {element}", "обновить значение элемента коллекции, id которого равен заданному", invoker);
        this.requiresModel = true;
    }

    @Override
    public void validateArgs(Request request) throws InvalidArgsException {
        super.validateArgs(request);
        long id = Long.parseLong(request.args()[0]);

        var flat = invoker.getCollection().get(id);
        if (flat == null) {
            throw new InvalidArgsException("Flat with specified ID doesn't exist");
        }

        var user = getUserByRequest(request);
        if (!user.getUsername().equals(flat.getOwner().getUsername())) {
            throw new InvalidArgsException("You can't modify flats you don't own");
        }
    }

    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);

        long flatId = Long.parseLong(request.args()[0]);
        var flat = flatRepository.findById(flatId).orElseThrow(() -> new InvalidArgsException("Flat with specified ID doesn't exist"));

        flat.update(request.getModel());
        flat = flatRepository.save(flat);

        invoker.getCollection().update(flatId, flat);
        invoker.getServer().broadcastResponse(new UpdateModelResponse(flat));

        return new CommandResponse("Flat (ID %s) updated successfully.".formatted(flatId), StatusCode.OK, request.credentials());
    }

    @Override
    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
