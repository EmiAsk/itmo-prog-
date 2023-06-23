package se.ifmo.lab08.server.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ifmo.lab08.common.dto.CommandDTO;
import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.ValidationRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.Response;
import se.ifmo.lab08.common.dto.response.ValidationResponse;
import se.ifmo.lab08.common.exception.AuthorizationException;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.common.exception.RoleException;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.server.command.*;
import se.ifmo.lab08.server.network.Server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    private final AuthManager authManager;
    private final RoleManager roleManager;
    private final CollectionManager collection;
    private Server server;
    private final IOProvider provider;

    public CommandManager(CollectionManager collection, IOProvider provider, AuthManager authManager, RoleManager roleManager) {
//        register("info", new InfoCommand(this));
//        register("show", new ShowCommand(this));
        register("add", new AddCommand(this));
        register("update", new UpdateCommand(this));
        register("remove_by_id", new RemoveByIdCommand(this));
        register("clear", new ClearCommand(this));
        register("remove_last", new RemoveLastCommand(this));
//        register("add_if_min", new AddIfMinCommand(provider, collection));
        register("shuffle", new ShuffleCommand(this));
        register("remove_all_by_furnish", new RemoveByFurnishCommand(this));
//        register("filter_starts_with_name", new FilterNameCommand(this));
//        register("print_unique_house", new PrintUniqueHouseCommand(this));
        register("sign_up", new SignUpCommand(this));
        register("login", new LoginCommand(this));
        register("logout", new LogoutCommand(this));
        register("change_role", new ChangeRoleCommand(this));
        this.authManager = authManager;
        this.roleManager = roleManager;
        this.collection = collection;
        this.provider = provider;
    }

    public List<CommandDTO> getCommandsDTO(Role role) {
        if (role == null) {
            return commands.entrySet()
                    .stream()
                    .filter(c -> c.getValue() instanceof Unauthorized)
                    .map(c -> new CommandDTO(
                            c.getKey(),
                            c.getValue().getDescription(),
                            c.getValue().getArgumentTypes(),
                            c.getValue().isRequiresModel())
                    )
                    .toList();
        }

        return commands.entrySet()
                .stream()
                .filter(c -> {
                            try {
                                roleManager.check(c.getKey(), role);
                                return true;
                            } catch (RoleException e) {
                                return false;
                            }
                        }
                )
                .map(c -> new CommandDTO(
                        c.getKey(),
                        c.getValue().getDescription(),
                        c.getValue().getArgumentTypes(),
                        c.getValue().isRequiresModel())
                )
                .toList();
    }

    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public Response execute(CommandRequest request) {
        try {
            if (!commands.containsKey(request.getName())) {
                return new CommandResponse("Invalid command", StatusCode.ERROR, request.credentials());
            }
            if (!(commands.get(request.getName()) instanceof Unauthorized)) {
                var user = authManager.authorize(request.credentials());
                roleManager.check(request.getName(), user.getRole());
            }
            var command = commands.get(request.getName());
            return command.execute(request);
        } catch (InvalidArgsException | RoleException e) {
            logger.error(e.toString());
            return new CommandResponse(e.getMessage(), StatusCode.ERROR, request.credentials());
        } catch (AuthorizationException e) {
            logger.error(e.toString());
            return new CommandResponse(e.getMessage(), StatusCode.ERROR);
        } catch (SQLException e) {
            logger.error(e.toString());
            return new CommandResponse("Something went wrong with DB", StatusCode.ERROR);
        }
    }

    public Response validate(ValidationRequest request) {
        try {
            if (!commands.containsKey(request.getName())) {
                return new ValidationResponse("Invalid command", StatusCode.ERROR, request.credentials());
            }
            if (!(commands.get(request.getName()) instanceof Unauthorized)) {
                var user = authManager.authorize(request.credentials());
                roleManager.check(request.getName(), user.getRole());
            }
            var command = commands.get(request.getName());
            command.validateArgs(request);
            return new ValidationResponse("OK", StatusCode.OK, request.credentials());
        } catch (InvalidArgsException | RoleException e) {
            logger.error(e.toString());
            return new ValidationResponse(e.getMessage(), StatusCode.ERROR, request.credentials());
        } catch (AuthorizationException e) {
            logger.error(e.toString());
            return new ValidationResponse(e.getMessage(), StatusCode.ERROR);
        }
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public AuthManager getAuthManager() {
        return authManager;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    public CollectionManager getCollection() {
        return collection;
    }

    public Server getServer() {
        return server;
    }

    public IOProvider getProvider() {
        return provider;
    }
}
