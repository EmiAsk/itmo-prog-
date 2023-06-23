package se.ifmo.lab08.client.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.ifmo.lab08.client.App;
import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.resourcebundles.enums.RuntimeOutputs;
import se.ifmo.lab08.client.resourcebundles.enums.UserFormElements;
import se.ifmo.lab08.client.tablehandlers.UserColumnNames;
import se.ifmo.lab08.client.util.AlertPrinter;
import se.ifmo.lab08.client.util.NotificationPrinter;
import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.dto.request.UserCollectionRequest;
import se.ifmo.lab08.common.entity.User;
import se.ifmo.lab08.common.util.IOProvider;
import se.ifmo.lab08.common.util.Printer;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserFormController {

    @FXML
    private Label commandLabel;

    @FXML
    private Button changeRoleButton;

    @FXML
    private Button backToTableButton;

    @FXML
    private TableView<User> tableView;

    private volatile static ObservableList<User> userCollection = FXCollections.observableArrayList();

    private static IOProvider provider = new IOProvider(new Scanner(System.in), new NotificationPrinter());

    private static CommandManager commandManager = new CommandManager(Client.getInstance(), provider, 0);

    private static final Client client = Client.getInstance();

    private static final Printer errorPrinter = new AlertPrinter();

    @FXML
    public void initialize() {
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
        updateLocale();

        try {
            Client.getInstance().send(new UserCollectionRequest());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(RuntimeOutputs.FAILED_TO_LOAD_DATA.toString());
            alert.show();
            throw new RuntimeException(e);
        }

        TableColumn<User, Integer> idColumn = new TableColumn<>(UserColumnNames.USER_ID_COLUMN.toString());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<User, String> usernameColumn = new TableColumn<>(UserColumnNames.USER_USERNAME_COLUMN.toString());
        usernameColumn.setCellValueFactory(cellValue -> new SimpleObjectProperty<>(cellValue.getValue().getUsername()));
        TableColumn<User, Role> roleColumn = new TableColumn<>(UserColumnNames.USER_ROLE_COLUMN.toString());
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        tableView.getColumns().setAll(idColumn, usernameColumn, roleColumn);

        tableView.setItems(userCollection);
    }

    protected void updateLocale() {
        commandLabel.setText(UserFormElements.COMMAND_LABEL.toString());
        changeRoleButton.setText(UserFormElements.CHANGE_ROLE_BUTTON.toString());
        backToTableButton.setText(UserFormElements.BACK_TO_TABLE_BUTTON.toString());
    }

    protected ChangeRoleFormController initChangeRoleForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/ChangeRoleForm.fxml"));
        Parent node = fxmlLoader.load();
        Scene scene = new Scene(node);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("main.ico"));
        stage.setTitle("Flat Realtor");
        ChangeRoleFormController controller = fxmlLoader.getController();
        controller.setCurrentStage(stage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        return controller;
    }

    @FXML
    protected void onChangeRoleButtonPressed(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setDisable(true);

        try {
            User user = tableView.getSelectionModel().getSelectedItem();
            if (user == null) {
                provider.getPrinter().print(RuntimeOutputs.USER_WAS_NOT_SELECTED_IN_TABLE.toString());
                return;
            }
            if (user.getUsername().equals(client.credentials().getUsername())) {
                provider.getPrinter().print(RuntimeOutputs.CHANGE_OWN_ROLE.toString());
                return;
            }
            var controller = initChangeRoleForm();
            var role = controller.getRole();
            if (role == null) {
                return;
            }
            var id = String.valueOf(user.getId());
            commandManager.executeServerCommand("change_role", new String[]{id, role.name()}, null);
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
    protected void onBackToTableButtonPressed(ActionEvent actionEvent) {
        App.getPrimaryStage().setResizable(true);
        App.getPrimaryStage().setScene(MainFormController.getMainFormController().getPrimaryScene());
    }

    public static void addUser(User user) {
        Platform.runLater(() -> userCollection.add(user));
    }

    public static void removeUser(int id) {
        Platform.runLater(() -> userCollection.removeIf(u -> id == u.getId()));
    }

    public static void updateUser(User user) {
        if (userCollection.removeIf(oldUser -> Objects.equals(oldUser.getId(), user.getId()))) {
            userCollection.add(user);
        }
    }

    public static void updateUserCollection(List<User> users) {
        userCollection.clear();
        userCollection.addAll(users);
    }
}
