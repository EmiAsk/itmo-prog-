package se.ifmo.lab08.client.parser;

import se.ifmo.lab08.client.Configuration;
import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.resourcebundles.enums.RuntimeOutputs;
import se.ifmo.lab08.common.dto.request.GetInfoRequest;
import se.ifmo.lab08.common.dto.response.GetInfoResponse;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.exception.*;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandParser extends DefaultParser {
    private final CommandManager commandManager;
    private final IOProvider provider;
    private final Client client;
    private final static int MAX_REC_DEPTH = Configuration.MAX_REC_DEPTH;
    private final int recDepth;

    public CommandParser(Client client, CommandManager commandManager, IOProvider provider, int recDepth) {
        super(provider.getScanner(), provider.getPrinter());
        this.commandManager = commandManager;
        this.provider = provider;
        this.recDepth = recDepth;
        this.client = client;
    }

    public void run() {
        Scanner scanner = provider.getScanner();
        Printer printer = provider.getPrinter();
        if (recDepth > MAX_REC_DEPTH) {
            throw new RecursionException();
        }
        while (true) {
            try {
                printer.printf("\n%s:\n", RuntimeOutputs.ENTER_COMMAND);
                String line = scanner.nextLine();
                printer.print(line);
                String[] splitLine = line.strip().split("\s+");
                String commandName = splitLine[0].toLowerCase();
                String[] args = Arrays.copyOfRange(splitLine, 1, splitLine.length);

                commandManager.getServerCommands().clear();
                client.send(new GetInfoRequest());
                var response = (GetInfoResponse) client.getResponse();
                commandManager.register(response.commands());
                client.setCredentials(response.credentials());

                if (commandManager.getClientCommand(commandName).isPresent()) {
                    commandManager.executeClientCommand(commandName, args);
                    continue;
                }
                var serverCommand = commandManager.getServerCommand(commandName);
                if (serverCommand.isEmpty()) {
                    printer.print(RuntimeOutputs.COMMAND_NOT_FOUND.toString());
                    continue;
                }
                Flat model = null;
                if (serverCommand.get().modelRequired()) {
                    model = new FlatParser(provider.getScanner(), provider.getPrinter()).parseFlat();
                }

                commandManager.executeServerCommand(commandName, args, model);
            } catch (InterruptCommandException e) {
                printer.printf("\n%s\n\n", RuntimeOutputs.INTERRUPTED_INPUT);
            } catch (NoSuchElementException e) {
                printer.print(RuntimeOutputs.END_OF_SCRIPT.toString());
                break;
            } catch (InvalidArgsException e) {
                printer.print(RuntimeOutputs.INVALID_ARGS.toString());
            } catch (AuthorizationException | RoleException e) {
                printer.print(RuntimeOutputs.COMMAND_NOT_FOUND.toString());
            } catch (TimeLimitExceededException e) {
                printer.print(RuntimeOutputs.SERVER_DOES_NOT_RESPOND.toString());
            } catch (RecursionException e) {
                printer.print(RuntimeOutputs.RECURSION_ERROR.toString());
                break;
            } catch (IOException | ClassNotFoundException e) {
                printer.print(RuntimeOutputs.SOMETHING_WENT_WRONG.toString());
            }
        }
    }
}


