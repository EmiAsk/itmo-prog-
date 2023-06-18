package se.ifmo.lab08.client.util;

import javafx.scene.control.Alert;
import se.ifmo.lab08.common.util.Printer;

public class AlertPrinter implements Printer {
    @Override
    public void print(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.show();
    }

    @Override
    public void printf(String format, Object... args) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(format.formatted(args));
        alert.show();
    }
}
