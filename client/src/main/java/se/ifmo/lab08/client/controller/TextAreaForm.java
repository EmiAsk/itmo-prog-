package se.ifmo.lab08.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TextAreaForm {
    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() {
        textArea.setEditable(false);
    }

    public void setTextArea(String text) {
        textArea.setText(text);
    }
}
