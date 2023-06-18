package se.ifmo.lab08.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.ifmo.lab08.common.util.CLIPrinter;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;
import se.ifmo.lab08.server.manager.*;
import se.ifmo.lab08.server.network.Server;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {

        try {
            var port = Configuration.PORT;
//            DatabaseManager.drop();
//            DatabaseManager.init();
            Scanner scanner = new Scanner(System.in);
            Printer printer = new CLIPrinter();
            IOProvider provider = new IOProvider(scanner, printer);

            AuthManager authManager = new AuthManager();
            CollectionManager collectionManager = BlockingCollectionManager.fromDatabase();
            RoleManager roleManager = RoleManager.create();

            CommandManager commandManager = new CommandManager(collectionManager, provider, authManager, roleManager);
            try (var server = new Server(commandManager, roleManager, port)) {
                commandManager.setServer(server);
                server.run();
            }
        } catch (Exception e) {
            logger.error("Error. Something went wrong:\n{}", e.getMessage());
        }
    }
}

