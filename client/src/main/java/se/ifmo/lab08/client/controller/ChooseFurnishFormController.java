package se.ifmo.lab08.client.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.ifmo.lab08.client.resourcebundles.enums.ChooseFurnishFormElements;
import se.ifmo.lab08.common.entity.Furnish;

public class ChooseFurnishFormController {

    private Stage currentStage;

    private Furnish furnish;

    @FXML
    private Label furnishLabel;

    @FXML
    private ComboBox<Furnish> furnishComboBox;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    protected void initialize() {
        furnishComboBox.setItems(FXCollections.observableArrayList(Furnish.values()));
        furnishComboBox.getItems().add(null);
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
        updateLocale();
    }

    protected void updateLocale() {
        furnishComboBox.setPromptText(ChooseFurnishFormElements.FURNISH_COMBOBOX.toString());
        furnishLabel.setText(ChooseFurnishFormElements.FURNISH_LABEL.toString());
        okButton.setText(ChooseFurnishFormElements.OK_BUTTON.toString());
        cancelButton.setText(ChooseFurnishFormElements.CANCEL_BUTTON.toString());
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
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
    protected void onOkButtonPressed(ActionEvent actionEvent) {
        furnish = furnishComboBox.getValue();
//        Notifications.create().text(RuntimeOutputs.FIELDS_DOES_NOT_VALID.toString()).position(Pos.TOP_CENTER).show();
        currentStage.close();
    }

    @FXML
    protected void onCloseButtonPressed(ActionEvent actionEvent) {
        currentStage.close();
    }

    public Furnish getFurnish() {
        return furnish;
    }
}


