package se.ifmo.lab08.client.command;

import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.common.exception.ExitException;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.common.util.IOProvider;

public class ExitCommand extends Command {
    public ExitCommand(IOProvider provider, Client client) {
        super("exit", "завершить программу (без сохранения в файл)", provider, client, Role.MIN_USER);
    }

    @Override
    public void execute(String[] args) throws InvalidArgsException {
        validateArgs(args);
        throw new ExitException();
    }
}
