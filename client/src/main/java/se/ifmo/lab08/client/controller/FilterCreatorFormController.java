package se.ifmo.lab08.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import se.ifmo.lab08.client.tablehandlers.FixedNameTableColumn;
import se.ifmo.lab08.client.tablehandlers.FlatColumnNames;
import se.ifmo.lab08.client.tablehandlers.TableViewHandler;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.AbstractPredicateFactory;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.FilterSigns;
import se.ifmo.lab08.client.tablehandlers.predicatefactory.PredicateFactory;
import se.ifmo.lab08.client.util.NotificationPrinter;
import se.ifmo.lab08.common.entity.Flat;
import se.ifmo.lab08.common.util.Printer;

import java.io.IOException;
import java.util.function.Predicate;

public class FilterCreatorFormController {
    @FXML
    protected GridPane currentPane;
    @FXML
    private ComboBox<FlatColumnNames> columnsForFilteringComboBox;
    @FXML
    private ComboBox<FilterSigns> signsCombobox;
    @FXML
    private TextField filteringValueTextField;
    @FXML
    private Label filterColumnLabel;
    @FXML
    private Label signLabel;
    @FXML
    private Label valueForFilteringLabel;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;

    private DatePicker datePicker;

    private final int DATE_PICKER_ROW = 2;

    private final int DATE_PICKER_COLUMN = 1;

    private Stage currentStage;

    private HBox mainFiltersHBox;

    private TableView<Flat> tableView;

    private final Printer printer = new NotificationPrinter();

    @FXML
    public void initialize() {
        mainFiltersHBox = MainFormController.getMainFormController().getFiltersHBox();
        tableView = MainFormController.getMainFormController().getTableView();
//        updateLocale();
        initColumns();
        columnsForFilteringComboBox.valueProperty().addListener(change -> columnsForFilteringComboBoxChanged());
        initSigns();
//        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
    }

    private void updateLocale() {
//        filterColumnLabel.setText(FilterCreatorFormElements.FILTER_COLUMN_LABEL.toString());
//        signLabel.setText(FilterCreatorFormElements.SIGN_LABEL.toString());
//        valueForFilteringLabel.setText(FilterCreatorFormElements.VALUE_FOR_FILTERING_LABEL.toString());
//        filteringValueTextField.setPromptText(FilterCreatorFormElements.FILTERING_VALUE_TEXT_FIELD.toString());
//        columnsForFilteringComboBox.setPromptText(FilterCreatorFormElements.COLUMNS_FOR_FILTERING_COMBO_BOX.toString());
//        signsCombobox.setPromptText(FilterCreatorFormElements.SIGNS_COMBO_BOX.toString());
//
//        createButton.setText(FilterCreatorFormElements.CREATE_BUTTON.toString());
//        cancelButton.setText(FilterCreatorFormElements.CANCEL_BUTTON.toString());
    }

    private void columnsForFilteringComboBoxChanged() {
        FlatColumnNames selectedColumn = columnsForFilteringComboBox.getValue();
        if (selectedColumn == FlatColumnNames.CREATION_DATE_COLUMN) {
            datePicker = new DatePicker();
            currentPane.getChildren().remove(filteringValueTextField);
            currentPane.add(datePicker, DATE_PICKER_COLUMN, DATE_PICKER_ROW);
            return;
        }
        if (datePicker != null) {
            currentPane.getChildren().remove(datePicker);
            currentPane.add(filteringValueTextField, 1, 2);
            datePicker = null;
        }
    }

    @FXML
    protected void onCancelButtonPressed(ActionEvent event) {
        currentStage.close();
    }

    @FXML
    protected void onCreateButtonPressed(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controller/FilterForm.fxml"));
            Node node = fxmlLoader.load();
            FilterFormController filterFormController = fxmlLoader.getController();
            if (isColumnSelected(filterFormController)
                    && isSignSelected(filterFormController)
                    && initPredicate(filterFormController)
            ) {
                filterFormController.setFilteringValueLabel(filteringValueTextField.getText());
                mainFiltersHBox.getChildren().add(node);
                currentStage.close();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(RuntimeOutputs.CAN_NOT_INIT_COMPONENT.toString());
            alert.setHeaderText("Can not init component");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private boolean initPredicate(FilterFormController filterFormController) {

        FixedNameTableColumn fixedNameTableColumn = (FixedNameTableColumn) tableView.getColumns().stream()
                .filter(x -> ((FixedNameTableColumn) x).getFixedName() == columnsForFilteringComboBox.getValue())
                .findAny().get();

        if (fixedNameTableColumn.getFixedName() == FlatColumnNames.CREATION_DATE_COLUMN && datePicker.getValue() != null) {
            filteringValueTextField.setText(datePicker.getValue().toString());
        }

        AbstractPredicateFactory abstractPredicateFactory = new AbstractPredicateFactory();
        PredicateFactory predicateFactory = abstractPredicateFactory.createFactory(fixedNameTableColumn.getFixedName());
        Predicate<Flat> predicate =
                predicateFactory.createPredicate(signsCombobox.getValue(),
                        filteringValueTextField.getText());
        TableViewHandler.getPredicates().add(predicate);
        filterFormController.setPredicate(predicate);
        return true;
    }

    private void initColumns() {
        var columns = tableView.getColumns();
        for (var tableColumn : columns) {
            columnsForFilteringComboBox.getItems().add(((FixedNameTableColumn<?, ?>) tableColumn).getFixedName());
        }
    }

    private void initSigns() {
        for (FilterSigns filterSigns : FilterSigns.values()) {
            signsCombobox.getItems().add(filterSigns);
        }
    }

    private boolean isColumnSelected(FilterFormController filterFormController) {
        if (columnsForFilteringComboBox.getValue() != null) {
            filterFormController.setColumnForFilteringLabel(columnsForFilteringComboBox.getValue());
            return true;
        }
        printer.print("Column was not selected");
//        Notifications.create().position(Pos.TOP_CENTER).text(RuntimeOutputs.COLUMN_WAS_NOT_SELECTED.toString()).show();
        return false;
    }

    private boolean isSignSelected(FilterFormController filterFormController) {
        if (signsCombobox.getValue() != null) {
            filterFormController.setFilterSignLabel(signsCombobox.getValue());
            return true;
        }
        printer.print("Sign was not selected");
//        Notifications.create().position(Pos.TOP_CENTER).text(RuntimeOutputs.SIGN_WAS_NOT_SELECTED.toString()).show();
        return false;
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

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }
}
