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
import javafx.stage.Stage;
import se.ifmo.lab08.client.App;
import se.ifmo.lab08.client.manager.CommandManager;
import se.ifmo.lab08.client.network.Client;
import se.ifmo.lab08.client.tablehandlers.UserColumnNames;
import se.ifmo.lab08.client.util.NotificationPrinter;
import se.ifmo.lab08.common.dto.Role;
import se.ifmo.lab08.common.dto.request.UserCollectionRequest;
import se.ifmo.lab08.common.entity.User;
import se.ifmo.lab08.common.util.IOProvider;

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

    @FXML
    public void initialize() {

        try {
            Client.getInstance().send(new UserCollectionRequest());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Unable to load users");
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

    protected ChangeRoleFormController initChangeRoleForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/ChangeRoleForm.fxml"));
        Parent node = fxmlLoader.load();
        Scene scene = new Scene(node);
        Stage stage = new Stage();
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
                provider.getPrinter().print("User was not selected in table");
                return;
            }
            var controller = initChangeRoleForm();
            var role = controller.getRole();
            if (role == null) {
                return;
            }
            var id = String.valueOf(user.getId());
            commandManager.executeServerCommand("change_role", new String[]{id, role.name()}, null);
        } catch (IOException | TimeLimitExceededException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Can not init component");
            alert.show();
            throw new RuntimeException(e);
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
