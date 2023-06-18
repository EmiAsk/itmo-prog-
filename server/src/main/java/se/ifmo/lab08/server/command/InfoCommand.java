package se.ifmo.lab08.server.command;


import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CommandManager;

public class InfoCommand extends Command {
    public InfoCommand(CommandManager invoker) {
        super("info", "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", invoker);
    }

    @Override
    public Response execute(CommandRequest request) throws InvalidArgsException {
        validateArgs(request);
        return new CommandResponse(invoker.getCollection().description(), StatusCode.OK, request.credentials());
    }
}
