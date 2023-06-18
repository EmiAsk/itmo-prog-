package se.ifmo.lab08.client.util;

import javafx.application.Platform;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;
import se.ifmo.lab08.common.util.Printer;

public class NotificationPrinter implements Printer {

    @Override
    public void print(String data) {
        Platform.runLater(() -> Notifications.create().text(data).position(Pos.TOP_CENTER).show());
    }

    @Override
    public void printf(String format, Object... args) {
        Platform.runLater(() -> Notifications.create().text(format.formatted(args)).position(Pos.TOP_CENTER).show());

    }
}
