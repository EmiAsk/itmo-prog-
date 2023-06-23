package se.ifmo.lab08.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.ifmo.lab08.client.controller.AuthorizationFormController;
import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.parser.CommandParser;
import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.dto.request.GetInfoRequest;
import se.ifmo.lab08.common.dto.response.GetInfoResponse;
import se.ifmo.lab08.common.exception.ExitException;
import se.ifmo.lab08.common.util.CLIPrinter;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class App extends Application {

    public final static int MAIN_SCENE_WIDTH = 800;
    public final static int MAIN_SCENE_HEIGHT = 600;
    private static final String HOST_NAME = "127.0.0.1";

    private static final int MAX_PORT_VALUE = 1023;

    private static final int PORT_INDEX = 0;

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/controller/AuthorizationForm.fxml"));
        primaryStage = stage;
        stage.getIcons().add(new Image("main.ico"));
        stage.setTitle("Flat Realtor");
        Scene scene = new Scene(parent, MAIN_SCENE_WIDTH, MAIN_SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setMinWidth(MAIN_SCENE_WIDTH);
        stage.setMinHeight(MAIN_SCENE_HEIGHT);
        stage.setOpacity(0.95);
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Printer printer = new CLIPrinter();
            IOProvider provider = new IOProvider(scanner, printer);

            try (var client = Client.connect(Configuration.HOST, Configuration.PORT)) {
                new Thread(client::startListening).start();
//                var manager = new CommandManager(client, provider, 0);
                launch(args);
            }
        } catch (ExitException e) {
            System.out.println("Program has finished. Good luck!");
        } catch (Exception e) {
            System.out.printf("Error occurred:\n%s", e.getMessage());
            System.exit(-1);
        }
    }
}
