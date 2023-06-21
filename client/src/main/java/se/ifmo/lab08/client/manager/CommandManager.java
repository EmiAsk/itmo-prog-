package se.ifmo.lab08.client.manager;

import se.ifmo.lab08.client.command.*;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.common.dto.CommandDTO;
import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.dto.StatusCode;
import se.ifmo.lab08.common.dto.request.CommandRequest;
import se.ifmo.lab08.common.dto.request.GetInfoRequest;
import se.ifmo.lab08.common.dto.request.ValidationRequest;
import se.ifmo.lab08.common.dto.response.CommandResponse;
import se.ifmo.lab08.common.dto.response.GetInfoResponse;
import se.ifmo.lab08.common.dto.response.ValidationResponse;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.exception.AuthorizationException;
import se.ifmo.lab08.common.exception.InvalidArgsException;
import se.ifmo.lab08.common.exception.RoleException;
import se.ifmo.lab08.common.util.ArgumentValidator;
import se.ifmo.lab08.common.util.IOProvider;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandManager {
    private final Map<String, CommandDTO> serverCommands = new HashMap<>();
    private final Map<String, Command> clientCommands = new HashMap<>();
    private final Client client;
    private final IOProvider provider;

    public CommandManager(Client client, IOProvider provider, int recDepth) {
        clientCommands.put("help", new HelpCommand(provider, client, clientCommands, serverCommands));
        clientCommands.put("execute_script", new ExecuteScriptCommand(provider, client, recDepth));
        clientCommands.put("exit", new ExitCommand(provider, client));
        this.client = client;
        this.provider = provider;
    }

    public void register(List<CommandDTO> commandsToAdd) {
        serverCommands.putAll(commandsToAdd.stream().collect(Collectors.toMap(CommandDTO::name, Function.identity())));
    }

    public Optional<CommandDTO> getServerCommand(String commandName) {
        return Optional.ofNullable(serverCommands.get(commandName));
    }

    public Optional<Command> getClientCommand(String commandName) {
        return Optional.ofNullable(clientCommands.get(commandName));
    }

    public Map<String, CommandDTO> getServerCommands() {
        return serverCommands;
    }

    private void loadCommands() throws IOException, TimeLimitExceededException {
        serverCommands.clear();
        client.send(new GetInfoRequest());
        var response = (GetInfoResponse) client.getResponse();
        register(response.commands());
        client.setCredentials(response.credentials());
    }

    public void executeClientCommand(String commandName, String[] args) throws IOException, TimeLimitExceededException, ClassNotFoundException {
        loadCommands();
        var clientCommand = getClientCommand(commandName);
        if (clientCommand.isEmpty()) {
            return;
        }
        if (clientCommand.get() instanceof Authorized) {
            if (client.credentials() == null) {
                throw new AuthorizationException();
            } else if (client.credentials().getRole().weight() < clientCommand.get().getRole().weight()) {
                throw new RoleException("Permission denied");
            }
        }
        clientCommand.get().execute(args);
    }

    public void executeServerCommand(String commandName, String[] args, Flat model) throws IOException, TimeLimitExceededException, ClassNotFoundException {
        loadCommands();
        var serverCommand = getServerCommand(commandName);

        if (serverCommand.isEmpty()) {
            return;
        }
        if (!ArgumentValidator.validate(serverCommand.get().args(), args)) {
            throw new InvalidArgsException();
        }
        client.send(new ValidationRequest(serverCommand.get().name(), args));
        var validateResponse = (ValidationResponse) client.getResponse();
        if (validateResponse.status() != StatusCode.OK) {
            throw new InvalidArgsException(validateResponse.message());
        }

        var commandRequest = new CommandRequest(serverCommand.get().name(), args);
        if (serverCommand.get().modelRequired()) {
            commandRequest.setModel(model);
        }
        commandRequest.setCredentials(client.credentials());
        client.send(commandRequest);
        var commandResponse = (CommandResponse) client.getResponse();
        System.out.println(provider.getPrinter().getClass().getSimpleName());
        client.setCredentials(commandResponse.credentials());
        provider.getPrinter().print(commandResponse.message());
    }

    public List<String> getCommandNamesByRole(Role role) throws IOException, TimeLimitExceededException {
        loadCommands();
        var names = new ArrayList<>(serverCommands.values().stream().map(CommandDTO::name).toList());
        names.addAll(clientCommands.values().stream().filter(p -> p.getRole().weight() <= role.weight()
        ).map(Command::getName).toList());
        return names;
    }
}
