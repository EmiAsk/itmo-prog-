package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.RemoveModelResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.entity.Furnish;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

import java.util.List;

public class RemoveByFurnishCommand extends Command {

    private static final Class<?>[] ARGS = new Class<?>[]{Furnish.class};

    public RemoveByFurnishCommand(CommandManager invoker) {
        super("remove_all_by_furnish {furnish}",
                "удалить из коллекции все элементы, значение поля furnish которого эквивалентно заданному",
                invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);

        Furnish furnish = Furnish.valueOf(request.args()[0]);
        var user = getUserByRequest(request);

        List<Long> ids = invoker.getCollection().removeByFurnish(user.getUsername(), furnish);

        invoker.getServer().broadcastResponse(new RemoveModelResponse(ids));

        return new CommandResponse("%s flats with Furnish [%s] removed successfully.\n".formatted(ids.size(), furnish), StatusCode.OK, request.credentials());
    }

    @Override
    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
