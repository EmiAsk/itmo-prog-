package se.ifmo.lab08.client;

import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.parser.CommandParser;
import se.ifmo.lab08.common.exception.ExitException;
import se.ifmo.lab08.common.util.CLIPrinter;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Printer printer = new CLIPrinter();
            IOProvider provider = new IOProvider(scanner, printer);

            try (var client = Client.connect(Configuration.HOST, Configuration.PORT)) {
                var manager = new CommandManager(client, provider, 0);
                var parser = new CommandParser(client, manager, provider, 0);
                parser.run();
            }
        } catch (ExitException e) {
            System.out.println("Program has finished. Good luck!");
        } catch (Exception e) {
            System.out.printf("Error occurred:\n%s", e.getMessage());
            System.exit(-1);
        }
    }
}