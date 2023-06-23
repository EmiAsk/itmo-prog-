package se.ifmo.lab08.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.ifmo.lab08.client.resourcebundles.enums.FlatFormElements;
import se.ifmo.lab08.client.resourcebundles.enums.RuntimeOutputs;
import se.ifmo.lab08.client.util.FlatFieldsValidator;
import se.ifmo.lab08.client.util.NotificationPrinter;
import se.ifmo.lab08.common.entity.*;
import se.ifmo.lab08.common.util.Printer;

public class FlatCreatingAndUpdatingFormController {
    @FXML
    private TextField bandNameTextField;
    private boolean isFlatNameValidity;
    @FXML
    private TextField coordinateXTextField;

    private boolean isCoordinateXValidity;
    @FXML
    private TextField coordinateYTextField;
    private boolean isCoordinateYValidity;
    @FXML
    private TextField areaTextField;
    private boolean isAreaValidity;
    @FXML
    private TextField numberOfRoomsTextField;
    private boolean isNumberOfRoomsValidity;
    @FXML
    private ComboBox<Furnish> furnishComboBox;
    private boolean isFurnishValidity;
    @FXML
    private ComboBox<View> viewComboBox;
    private boolean isViewValidity;
    @FXML
    private ComboBox<Transport> transportComboBox;
    private boolean isTransportValidity;
    @FXML
    private TextField houseNameTextField;
    private boolean isHouseNameValidity;
    @FXML
    public TextField houseYearTextField;
    private boolean isHouseYearValidity;
    @FXML
    private TextField houseNumberOfFlatsTextField;
    private boolean isHouseNumberOfFlatsValidity;

    @FXML
    private Label bandNameLabel;

    @FXML
    private Label coordinateXLabel;

    @FXML
    private Label coordinateYLabel;

    @FXML
    private Label areaLabel;

    @FXML
    private Label numberOfRoomsLabel;

    @FXML
    private Label furnishLabel;

    @FXML
    private Label viewLabel;

    @FXML
    private Label transportLabel;

    @FXML
    private Label houseNameLabel;

    @FXML
    private Label houseYearLabel;

    @FXML
    private Label houseNumberOfFlatsLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    private Flat flat;


    private Stage currentStage;

    private static Printer printer = new NotificationPrinter();

    @FXML
    public void initialize() {
        initListeners();
        preValidation();
        initComboBoxes();
        updateLocale();
        MainFormController.getCurrentLocale().addListener(change -> updateLocale());
    }

    private void updateLocale() {

        bandNameTextField.setPromptText(FlatFormElements.FLAT_NAME_TEXT_FIELD.toString());
        coordinateXTextField.setPromptText(FlatFormElements.COORDINATE_X_TEXT_FIELD.toString());
        coordinateYTextField.setPromptText(FlatFormElements.COORDINATE_Y_TEXT_FIELD.toString());
        areaTextField.setPromptText(FlatFormElements.AREA_TEXT_FIELD.toString());
        numberOfRoomsTextField.setPromptText(FlatFormElements.NUMBER_OF_ROOMS_TEXT_FIELD.toString());
        furnishComboBox.setPromptText(FlatFormElements.FURNISH_COMBO_BOX.toString());
        viewComboBox.setPromptText(FlatFormElements.VIEW_COMBO_BOX.toString());
        transportComboBox.setPromptText(FlatFormElements.TRANSPORT_COMBO_BOX.toString());
        houseNameTextField.setPromptText(FlatFormElements.HOUSE_NAME_TEXT_FIELD.toString());
        houseYearTextField.setPromptText(FlatFormElements.HOUSE_YEAR_TEXT_FIELD.toString());
        houseNumberOfFlatsTextField.setPromptText(FlatFormElements.HOUSE_NUMBER_OF_FLATS_TEXT_FIELD.toString());

        bandNameLabel.setText(FlatFormElements.FLAT_NAME_LABEL.toString());
        coordinateXLabel.setText(FlatFormElements.COORDINATE_X_LABEL.toString());
        coordinateYLabel.setText(FlatFormElements.COORDINATE_Y_LABEL.toString());
        areaLabel.setText(FlatFormElements.AREA_LABEL.toString());
        numberOfRoomsLabel.setText(FlatFormElements.NUMBER_OF_ROOMS_LABEL.toString());
        furnishLabel.setText(FlatFormElements.FURNISH_LABEL.toString());
        viewLabel.setText(FlatFormElements.VIEW_LABEL.toString());
        transportLabel.setText(FlatFormElements.TRANSPORT_LABEL.toString());
        houseNameLabel.setText(FlatFormElements.HOUSE_NAME_LABEL.toString());
        houseYearLabel.setText(FlatFormElements.HOUSE_YEAR_LABEL.toString());
        houseNumberOfFlatsLabel.setText(FlatFormElements.HOUSE_NUMBER_OF_FLATS_LABEL.toString());
        okButton.setText(FlatFormElements.OK_Button.toString());
        cancelButton.setText(FlatFormElements.CANCEL_BUTTON.toString());
    }

    private void preValidation() {
        FlatFieldsValidator.flatNameValidate(bandNameTextField, this);
        FlatFieldsValidator.coordinateXValidate(coordinateXTextField, this);
        FlatFieldsValidator.coordinateYValidate(coordinateYTextField, this);
        FlatFieldsValidator.numberOfRoomsValidate(numberOfRoomsTextField, this);
        FlatFieldsValidator.areaValidate(areaTextField, this);
        FlatFieldsValidator.furnishValidate(furnishComboBox, this);
        FlatFieldsValidator.viewValidate(viewComboBox, this);
        FlatFieldsValidator.transportValidate(transportComboBox, this);
        FlatFieldsValidator.houseNameValidate(houseNameTextField, this);
        FlatFieldsValidator.houseYearValidate(houseYearTextField, this);
        FlatFieldsValidator.houseNumberOfFlatsValidate(houseNumberOfFlatsTextField, this);
    }

    private void initListeners() {
        bandNameTextField.textProperty().addListener(change -> FlatFieldsValidator.flatNameValidate(bandNameTextField, this));
        coordinateXTextField.textProperty().addListener(change -> FlatFieldsValidator.coordinateXValidate(coordinateXTextField, this));
        coordinateYTextField.textProperty().addListener(change -> FlatFieldsValidator.coordinateYValidate(coordinateYTextField, this));
        numberOfRoomsTextField.textProperty().addListener(change -> FlatFieldsValidator.numberOfRoomsValidate(numberOfRoomsTextField, this));
        areaTextField.textProperty().addListener(change -> FlatFieldsValidator.areaValidate(areaTextField, this));
        furnishComboBox.valueProperty().addListener(change -> FlatFieldsValidator.furnishValidate(furnishComboBox, this));
        viewComboBox.valueProperty().addListener(change -> FlatFieldsValidator.viewValidate(viewComboBox, this));
        transportComboBox.valueProperty().addListener(change -> FlatFieldsValidator.transportValidate(transportComboBox, this));
        houseNameTextField.textProperty().addListener(change -> FlatFieldsValidator.houseNameValidate(houseNameTextField, this));
        houseYearTextField.textProperty().addListener(change -> FlatFieldsValidator.houseYearValidate(houseYearTextField, this));
        houseNumberOfFlatsTextField.textProperty().addListener(change -> FlatFieldsValidator.houseNumberOfFlatsValidate(houseNumberOfFlatsTextField, this));
    }

    public void fillIn(Flat flat) {
        bandNameTextField.setText(flat.getName());
        coordinateXTextField.setText(String.valueOf(flat.getCoordinates().getX()));
        coordinateYTextField.setText(String.valueOf(flat.getCoordinates().getY()));
        areaTextField.setText(String.valueOf(flat.getArea()));
        numberOfRoomsTextField.setText(String.valueOf(flat.getNumberOfRooms()));
        furnishComboBox.setValue(flat.getFurnish());
        viewComboBox.setValue(flat.getView());
        transportComboBox.setValue(flat.getTransport());
        houseNameTextField.setText(flat.getHouse().getName());
        houseYearTextField.setText(String.valueOf(flat.getHouse().getYear()));
        houseNumberOfFlatsTextField.setText(String.valueOf(flat.getHouse().getNumberOfFlatsOnFloor()));
    }

    private void initComboBoxes() {
        for (Furnish furnish : Furnish.values()) {
            furnishComboBox.getItems().add(furnish);
        }
        for (View view : View.values()) {
            viewComboBox.getItems().add(view);
        }
        for (Transport transport : Transport.values()) {
            transportComboBox.getItems().add(transport);
        }
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
        if (checkValidities()) {
            flat = collectFlat();
            currentStage.close();
            return;
        }
        printer.print(RuntimeOutputs.FIELDS_NOT_VALID.toString());
//        Notifications.create().text(RuntimeOutputs.FIELDS_DOES_NOT_VALID.toString()).position(Pos.TOP_CENTER).show();
    }

    @FXML
    protected void onCloseButtonPressed(ActionEvent actionEvent) {
        currentStage.close();
    }

    private Flat collectFlat() {
        var flat = new Flat();
        flat.setName(bandNameTextField.getText());
        long coordinateX = Long.parseLong(coordinateXTextField.getText());
        float coordinateY = Float.parseFloat(coordinateYTextField.getText().replace(",", "."));
        flat.setCoordinates(new Coordinates(coordinateX, coordinateY));
        flat.setArea(Long.parseLong(areaTextField.getText()));
        var numberOfRooms = numberOfRoomsTextField.getText();
        flat.setNumberOfRooms(numberOfRooms.isEmpty() ? null : Long.parseLong(numberOfRooms));
        flat.setFurnish(furnishComboBox.getValue());
        flat.setView(viewComboBox.getValue());
        flat.setTransport(transportComboBox.getValue());

        var houseName = houseNameTextField.getText();
        var houseYear = houseYearTextField.getText().isEmpty() ? null : Long.parseLong(houseYearTextField.getText());
        var houseNumberOfFlats = Integer.parseInt(houseNumberOfFlatsTextField.getText());
        var house = new House()
                .setName(houseName)
                .setYear(houseYear)
                .setNumberOfFlatsOnFloor(houseNumberOfFlats);

        flat.setHouse(house);

        return flat;

    }
//        Map<DataField, Object> data = new HashMap();
//        data.put(DataField.NAME, bandNameTextField.getText());
//        int coordinateX = Integer.parseInt(coordinateXTextField.getText());
//        double coordinateY = Double.parseDouble(coordinateYTextField.getText().replace(",", "."));
//        Coordinates coordinates = new Coordinates(coordinateX,coordinateY);
//        data.put(DataField.COORDINATES, coordinates);
//        data.put(DataField.NUMBER_OF_PARTICIPANTS, Integer.parseInt(numberOfParticipantsTextField.getText()));
//        data.put(DataField.GENRE, genreComboBox.getValue());
//
//        String personName = personNameTextField.getText();
//        Float personHeight = personHeightTextField.getText().isEmpty() ? null : Float.parseFloat(personHeightTextField.getText().replace(",", "."));
//        Country nationality = personNationalityComboBox.getValue();
//        int locationX = Integer.parseInt(locationXTextField.getText());
//        float locationY = Float.parseFloat(locationYTextField.getText().replace(",", "."));
//        float locationZ = Float.parseFloat(locationZTextField.getText().replace(",", "."));
//        Person person = new Person(personName, personHeight, nationality, new Location(locationX, locationY, locationZ));
//
//        data.put(DataField.FRONTMAN, person);
//        return data;
//    }

    private boolean checkValidities() {
        return isFlatNameValidity && isCoordinateXValidity && isCoordinateYValidity
                && isAreaValidity && isNumberOfRoomsValidity && isFurnishValidity
                && isViewValidity && isTransportValidity && isHouseNameValidity
                && isHouseYearValidity && isHouseNumberOfFlatsValidity;
    }

    public void setFlatNameValidity(boolean flatNameValidity) {
        isFlatNameValidity = flatNameValidity;
    }

    public void setCoordinateXValidity(boolean coordinateXValidity) {
        isCoordinateXValidity = coordinateXValidity;
    }

    public void setCoordinateYValidity(boolean coordinateYValidity) {
        isCoordinateYValidity = coordinateYValidity;
    }

    public void setNumberOfRoomsValidity(boolean numberOfRoomsValidity) {
        isNumberOfRoomsValidity = numberOfRoomsValidity;
    }

    public void setAreaValidity(boolean areaValidity) {
        isAreaValidity = areaValidity;
    }

    public void setFurnishValidity(boolean furnishValidity) {
        isFurnishValidity = furnishValidity;
    }

    public void setViewValidity(boolean viewValidity) {
        isViewValidity = viewValidity;
    }

    public void setTransportValidity(boolean transportValidity) {
        isTransportValidity = transportValidity;
    }

    public void setHouseNameValidity(boolean houseNameValidity) {
        isHouseNameValidity = houseNameValidity;
    }

    public void setHouseYearValidity(boolean houseYearValidity) {
        isHouseYearValidity = houseYearValidity;
    }

    public void setHouseNumberOfFlatsValidity(boolean houseNumberOfFlatsValidity) {
        isHouseNumberOfFlatsValidity = houseNumberOfFlatsValidity;
    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public Flat getFlat() {
        return flat;
    }
}
