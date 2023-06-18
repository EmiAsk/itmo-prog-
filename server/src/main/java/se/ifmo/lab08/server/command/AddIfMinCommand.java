package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.server.manager.CollectionManager;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.server.manager.CommandManager;

import java.sql.SQLException;


@Deprecated
public class AddIfMinCommand extends Command {
    public AddIfMinCommand(CommandManager invoker) {
        super("add_if_min {element}",
                "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции",
                invoker);
        this.requiresModel = true;
    }

    @Override
    public Response execute(CommandRequest request) throws SQLException {
        validateArgs(request);

        var flat = request.getModel();
        var user = getUserByRequest(request);
        flat.setOwner(user);

        var minFlat = invoker.getCollection().min();
        if (minFlat.getArea() <= flat.getArea()) {
            var message = "Flat (value: %s) not added because there is flat with less value (%s).\n".formatted(
                    flat.getArea(), minFlat.getArea()
            );
            return new CommandResponse(message, StatusCode.OK, request.credentials());
        }
        flat = flatRepository.save(request.getModel());
        invoker.getCollection().push(flat);
        var message = "Flat (ID %s) added successfully.\n".formatted(flat.getId());
        return new CommandResponse(message, StatusCode.OK, request.credentials());
    }
}
