package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CollectionManager;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.server.manager.CommandManager;

public class FilterNameCommand extends Command {

    private static final Class<?>[] ARGS = new Class<?>[]{String.class};

    public FilterNameCommand(CommandManager invoker) {
        super("filter_starts_with_name {name}",
                "вывести элементы, значение поля name которых начинается с заданной подстроки", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);
        String name = request.args()[0];
        var flats = invoker.getCollection().filterByName(name);

        var builder = new StringBuilder();
        builder.append("Collection filtered by name:\n");
        var line = "-".repeat(60) + "\n";
        builder.append(line);
        for (Flat flat : flats) {
            builder.append(flat.toString()).append("\n");
            builder.append(line);
        }
        return new CommandResponse(builder.toString(), StatusCode.OK, request.credentials());
    }

    @Override
    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
