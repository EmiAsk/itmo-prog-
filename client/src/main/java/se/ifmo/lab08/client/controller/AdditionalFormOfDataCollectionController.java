package se.ifmo.lab08.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdditionalFormOfDataCollectionController {
    @FXML
    private TextField valueTextField;

    @FXML
    private Button cancelButton;

    private Stage currentStage;

    @FXML
    public void initialize() {
//        MainFormController.getCurrentLocale().addListener(change->updateLocale());
//        updateLocale();
    }

    private void updateLocale() {
    }

    @FXML
    protected void onOkButtonPressed(ActionEvent actionEvent) {
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
    protected void onCancelButtonPressed(ActionEvent event) {
        valueTextField.setText("");
        currentStage.close();
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

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setPromptText(String promptText) {
        valueTextField.setPromptText(promptText);
    }

    public String getResult() {
        return valueTextField.getText();
    }
}
