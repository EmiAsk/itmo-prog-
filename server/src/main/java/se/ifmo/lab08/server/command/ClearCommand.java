package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.response.RemoveModelResponse;
import se.ifmo.lab08.server.manager.CollectionManager;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;
import java.util.List;

public class ClearCommand extends Command {
    public ClearCommand(CommandManager invoker) {
        super("clear", "очистить коллекцию", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);
        var user = getUserByRequest(request);
        flatRepository.deleteByOwnerId(user.getId());
        List<Long> ids = invoker.getCollection().removeByOwnerId(user.getId());
        invoker.getServer().broadcastResponse(new RemoveModelResponse(ids));
        return new CommandResponse("All your %s flats cleared successfully.\n".formatted(ids.size()), StatusCode.OK, request.credentials());
    }
}
