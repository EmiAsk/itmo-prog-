package se.ifmo.lab08.client.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import se.ifmo.lab08.client.tablehandlers.FlatColumnNames;
import se.ifmo.lab08.client.tablehandlers.TableViewHandler;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.common.entity.Flat;

import java.util.function.Predicate;

public class FilterFormController {
    @FXML
    private GridPane filterFormMainPane;
    @FXML
    private Label columnForFilteringLabel;
    @FXML
    private Label filterSignLabel;
    @FXML
    private Label filteringValueLabel;

    private FlatColumnNames columnForFiltering;

    private Predicate<Flat> predicate;

    private EventHandler<Event> removeButtonPressedEventHandler;

    @FXML
    public void initialize() {
        removeButtonPressedEventHandler = this::removeListeners;
        MainFormController.getMainFormController().getRemoveFiltersButton().addEventHandler(MouseEvent.MOUSE_CLICKED, removeButtonPressedEventHandler);
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
    }

    private void updateLocale() {
        columnForFilteringLabel.setText(columnForFiltering.toString());
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
    private void onCloseButtonPressed(ActionEvent actionEvent) {
        MainFormController mainFormController = MainFormController.getMainFormController();
        mainFormController.getFiltersHBox().getChildren().remove(filterFormMainPane);
        removeListeners(null);
    }

    private void removeListeners(Event event) {
        MainFormController.getMainFormController().getRemoveFiltersButton().removeEventHandler(MouseEvent.MOUSE_CLICKED, removeButtonPressedEventHandler);
        TableViewHandler.getPredicates().remove(predicate);
    }

    public void setColumnForFilteringLabel(FlatColumnNames value) {
        columnForFiltering = value;
        columnForFilteringLabel.setText(value.toString());
    }

    public void setFilterSignLabel(FilterSigns value) {
        filterSignLabel.setText(value.toString());
    }

    public void setFilteringValueLabel(String value) {
        filteringValueLabel.setText(value);
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

}
