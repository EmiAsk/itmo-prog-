package se.ifmo.lab08.client.command;

import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.common.util.ArgumentValidator;
import se.ifmo.lab08.common.util.IOProvider;

public abstract class Command {
    private static final Class<?>[] ARGS = new Class<?>[]{};

    String name;
    String description;
    IOProvider provider;
    Client client;
    Role role;

    public Command(String name, String description, IOProvider provider, Client client, Role role) {
        this.name = name;
        this.description = description;
        this.provider = provider;
        this.client = client;
        this.role = role;
    }

    public abstract void execute(String[] args) throws InvalidArgsException;

    public String getDescription() {
        return String.format("%40s    |    %s", name, description);
    }

    public String getName() {
        return name;
    }

    public void validateArgs(String[] args) throws InvalidArgsException {
        if (!ArgumentValidator.validate(getArgumentTypes(), args)) {
            throw new InvalidArgsException();
        }
    }

    public Role getRole() {
        return role;
    }

    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }
}
