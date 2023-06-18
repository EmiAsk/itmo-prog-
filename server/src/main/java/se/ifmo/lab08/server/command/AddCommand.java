package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.AddModelResponse;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.server.manager.CollectionManager;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;

public class AddCommand extends Command {
    public AddCommand(CommandManager invoker) {
        super("add", "добавить новый элемент в коллекцию", invoker);
        this.requiresModel = true;
    }

    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);

        var flat = request.getModel();
        var user = getUserByRequest(request);
        flat.setOwner(user);

        flat = flatRepository.save(flat);
        invoker.getCollection().push(flat);
        invoker.getServer().broadcastResponse(new AddModelResponse(flat));

        var message = "Flat (ID %s) added successfully.\n".formatted(flat.getId());
        return new CommandResponse(message, StatusCode.OK, request.credentials());
    }
}
