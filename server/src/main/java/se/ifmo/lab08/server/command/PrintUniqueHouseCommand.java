package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.entity.House;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

public class PrintUniqueHouseCommand extends Command {
    public PrintUniqueHouseCommand(CommandManager invoker) {
        super("print_unique_house", "вывести уникальные значения поля house всех элементов в коллекции", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);
        var houseSet = invoker.getCollection().getUniqueHouses();

        String line = "-".repeat(60);
        var builder = new StringBuilder();
        builder.append(line).append("\n");
        for (House house : houseSet) {
            builder.append(house.toString()).append("\n");
            builder.append(line).append("\n");
        }
        return new CommandResponse(builder.toString(), StatusCode.OK, request.credentials());
    }
}
