package se.ifmo.lab08.server.command;

import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.Request;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.entity.User;
import se.ifmo.lab08.common.exception.AuthorizationException;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.server.manager.CollectionManager;
import se.ifmo.lab08.server.manager.CommandManager;
import se.ifmo.lab08.server.persistance.repository.FlatRepository;
import se.ifmo.lab08.server.persistance.repository.HouseRepository;
import se.ifmo.lab08.server.persistance.repository.UserRepository;
import se.ifmo.lab08.common.util.ArgumentValidator;
import se.ifmo.lab08.common.util.IOProvider;

import java.sql.SQLException;

public abstract class Command {

    private static final Class<?>[] ARGS = new Class<?>[]{};

    private final String name;
    private final String description;
    CommandManager invoker;
    boolean requiresModel;
    UserRepository userRepository;
    FlatRepository flatRepository;

    public Command(String name, String description, CommandManager invoker) {
        this.name = name;
        this.description = description;
        this.invoker = invoker;
        this.requiresModel = false;
        this.userRepository = new UserRepository();
        this.flatRepository = new FlatRepository(new HouseRepository(), userRepository);
    }

    public abstract Response execute(CommandRequest args) throws SQLException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return String.format("%40s    |     %s", name, description);
    }

    public boolean isRequiresModel() {
        return requiresModel;
    }

    public void validateArgs(Request request) {
        if (!ArgumentValidator.validate(getArgumentTypes(), request.args())) {
            throw new InvalidArgsException();
        }
    }

    public Class<?>[] getArgumentTypes() {
        return ARGS;
    }

    User getUserByRequest(Request request) {
        return userRepository.findByUsername(request.credentials().getUsername()).orElseThrow(() -> new AuthorizationException("Access denied"));
    }
}
