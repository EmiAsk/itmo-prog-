package se.ifmo.lab08.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import se.ifmo.lab08.client.App;
import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.resourcebundles.enums.AuthorizationFormElements;
import se.ifmo.lab08.client.resourcebundles.enums.RuntimeOutputs;
import se.ifmo.lab08.client.util.AlertPrinter;
import se.ifmo.lab08.client.util.NotificationPrinter;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class AuthorizationFormController {
    private int SHOW_PASSWORD_TOOLTIP_DELAY = 1000;

    private int SCENE_HEIGHT = 600;

    private int SCENE_WIDTH = 800;

    private final int LANGUAGE_CHANGING_FORM_WIDTH = 300;


    private final int LANGUAGE_CHANGING_FORM_HEIGHT = 200;

    @FXML
    private TextField logInTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Menu settingsMenu;

    @FXML
    private MenuItem languageMenuItem;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    private IOProvider provider = new IOProvider(new Scanner(System.in), new NotificationPrinter());

    private CommandManager commandManager = new CommandManager(Client.getInstance(), provider, 0);

    private Printer errorPrinter = new AlertPrinter();

    @FXML
    public void initialize() {
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
        updateLocale();
    }

    private void updateLocale() {
        logInTextField.setPromptText(AuthorizationFormElements.LOG_IN_TEXT_FIELD.toString());
        passwordTextField.setPromptText(AuthorizationFormElements.PASSWORD_TEXT_FIELD.toString());
        settingsMenu.setText(AuthorizationFormElements.SETTINGS_MENU.toString());
        languageMenuItem.setText(AuthorizationFormElements.LANGUAGE_MENU_ITEM.toString());
        loginLabel.setText(AuthorizationFormElements.LOGIN_LABEL.toString());
        passwordLabel.setText(AuthorizationFormElements.PASSWORD_LABEL.toString());
        signUpButton.setText(AuthorizationFormElements.SIGN_UP_BUTTON.toString());
        signInButton.setText(AuthorizationFormElements.SIGN_IN_BUTTON.toString());
    }

    @FXML
    protected void onPasswordFieldMouseEntered(MouseEvent mouseEvent) {
        passwordTextField.setTooltip(new Tooltip(passwordTextField.getText()));
        passwordTextField.getTooltip().textProperty().bindBidirectional(passwordTextField.textProperty());
        passwordTextField.getTooltip().setShowDelay(new Duration(SHOW_PASSWORD_TOOLTIP_DELAY));
    }

    @FXML
    protected void onPasswordFieldMouseExited(MouseEvent mouseEvent) {
        passwordTextField.setTooltip(null);
    }

    @FXML
    protected void onSignInButtonPressed(ActionEvent event) throws ExecutionException, InterruptedException {
        Button button = (Button) event.getTarget();
        button.setDisable(true);

        try {
            if (commandManager.executeServerCommand("login", new String[]{logInTextField.getText(), passwordTextField.getText()}, null)) {
                provider.getPrinter().print(RuntimeOutputs.LOGIN_SUCCESSFULLY.toString());
            }
            if (Client.getInstance().credentials() == null) {
                provider.getPrinter().print(RuntimeOutputs.FAILED_LOGIN.toString());
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DataGridForm.fxml"));
            Parent parent = fxmlLoader.load();
            MainFormController mainFormController = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            mainFormController.setPrimaryScene(scene);
            App.getPrimaryStage().setScene(scene);
        } catch (IOException | ClassNotFoundException e) {
            errorPrinter.print(RuntimeOutputs.SOMETHING_WENT_WRONG.toString());
            throw new RuntimeException(e);
        } catch (TimeLimitExceededException ignored) {
            errorPrinter.print(RuntimeOutputs.SERVER_DOES_NOT_RESPOND.toString());
        } finally {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onSignUpButtonPressed(ActionEvent event) throws ExecutionException, InterruptedException {
        Button button = (Button) event.getTarget();
        button.setDisable(true);

        try {
            if (commandManager.executeServerCommand("sign_up", new String[]{logInTextField.getText(), passwordTextField.getText()}, null)) {
                provider.getPrinter().print(RuntimeOutputs.SIGNUP_SUCCESSFULLY.toString());
            }
            ;
            if (Client.getInstance().credentials() == null) {
                provider.getPrinter().print(RuntimeOutputs.FAILED_SIGNUP.toString());
                return;
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DataGridForm.fxml"));
            Parent parent = fxmlLoader.load();
            MainFormController mainFormController = fxmlLoader.getController();
            Scene scene = new Scene(parent);
            mainFormController.setPrimaryScene(scene);
            App.getPrimaryStage().setScene(scene);
        } catch (IOException | ClassNotFoundException e) {
            errorPrinter.print(RuntimeOutputs.SOMETHING_WENT_WRONG.toString());
            throw new RuntimeException(e);
        } catch (TimeLimitExceededException ignored) {
            errorPrinter.print(RuntimeOutputs.SERVER_DOES_NOT_RESPOND.toString());
        } finally {
            button.setDisable(false);
        }
//        Command command = new SignUpCommand(Invoker.getInstance(), logInTextField.getText(), passwordTextField.getText());
//        if (Invoker.getInstance().invokeCommand(command).get()) {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DataGridForm.fxml"));
//                Parent parent = fxmlLoader.load();
//                MainFormController mainFormController = fxmlLoader.getController();
//                mainFormController.getUserMenu().setText(ClientInfo.getLogin() + "   ID:   " + ClientInfo.getUserId());
//                Scene scene = new Scene(parent, SCENE_WIDTH, SCENE_HEIGHT);
//                mainFormController.setPrimaryScene(scene);
//                MainApplication.getPrimaryStage().setScene(scene);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        button.setDisable(false);
    }

    @FXML
    protected void onButtonMouseEntered(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 2;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    @FXML
    protected void onButtonMouseExited(MouseEvent event) {
        Button button = (Button) event.getTarget();
        button.setStyle("""
                -fx-background-color: null;
                -fx-border-width: 1;
                -fx-border-radius: 50;
                -fx-border-color: brown
                """);
    }

    @FXML
    protected void onLanguageMenuItemPressed(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LanguageChangingFormController.class.getResource("/controller/LanguageChangingForm.fxml"));
            Parent parent = fxmlLoader.load();
            LanguageChangingFormController languageChangingFormController = fxmlLoader.getController();
            Scene scene = new Scene(parent, LANGUAGE_CHANGING_FORM_WIDTH, LANGUAGE_CHANGING_FORM_HEIGHT);
            Stage stage = new Stage();
            stage.getIcons().add(new Image("main.ico"));
            languageChangingFormController.setCurrentStage(stage);
            stage.setTitle("Flat Realtor");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            errorPrinter.print(RuntimeOutputs.SOMETHING_WENT_WRONG.toString());
        }
    }
}
