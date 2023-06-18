package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

@Deprecated
public class ShowCommand extends Command {
    public ShowCommand(CommandManager invoker) {
        super("show", "вывести все элементы коллекции в строковом представлении", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);
        var builder = new StringBuilder();
        String line = "-".repeat(60);
        builder.append(line).append("\n");
        for (Flat flat : invoker.getCollection().getCollection()) {
            builder.append(flat.toString()).append("\n");
            builder.append(line).append("\n");
        }
        return new CommandResponse(builder.toString(), StatusCode.OK, request.credentials());
    }
}
