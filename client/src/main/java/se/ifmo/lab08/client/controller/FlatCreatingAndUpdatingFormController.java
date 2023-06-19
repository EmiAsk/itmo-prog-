package se.ifmo.lab08.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.ifmo.lab08.common.entity.*;

public class FlatCreatingAndUpdatingFormController {
    @FXML
    private TextField bandNameTextField;
    private boolean isBandNameValidity;
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
    private boolean isNumberOfRoomsTextFieldValidity;
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
    private boolean isHouseNumberOfFlatsTextFieldValidity;

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

    @FXML
    public void initialize() {
        initListeners();
        preValidation();
        initComboBoxes();
//        updateLocale();
//        MainFormController.getCurrentLocale().addListener(change->updateLocale());
    }

    private void updateLocale() {
//
//        bandNameTextField.setPromptText(FlatCreatingAndUpdatingFormElements.BAND_NAME_TEXT_FIELD.toString());
//        coordinateXTextField.setPromptText(FlatCreatingAndUpdatingFormElements.COORDINATE_X_TEXT_FIELD.toString());
//        coordinateYTextField.setPromptText(FlatCreatingAndUpdatingFormElements.COORDINATE_Y_TEXT_FIELD.toString());
//        numberOfParticipantsTextField.setPromptText(FlatCreatingAndUpdatingFormElements.NUMBER_OF_PARTICIPANTS_TEXT_FIELD.toString());
//        genreComboBox.setPromptText(FlatCreatingAndUpdatingFormElements.GENRE_COMBO_BOX.toString());
//        personNameTextField.setPromptText(FlatCreatingAndUpdatingFormElements.PERSON_NAME_TEXT_FIELD.toString());
//        personHeightTextField.setPromptText(FlatCreatingAndUpdatingFormElements.PERSON_HEIGHT_TEXT_FIELD.toString());
//        personNationalityComboBox.setPromptText(FlatCreatingAndUpdatingFormElements.PERSON_NATIONALITY_COMBO_BOX.toString());
//        locationXTextField.setPromptText(FlatCreatingAndUpdatingFormElements.LOCATION_X_TEXT_FIELD.toString());
//        locationYTextField.setPromptText(FlatCreatingAndUpdatingFormElements.LOCATION_Y_TEXT_FIELD.toString());
//        locationZTextField.setPromptText(FlatCreatingAndUpdatingFormElements.LOCATION_Z_TEXT_FIELD.toString());
//
//        bandNameLabel.setText(FlatCreatingAndUpdatingFormElements.BAND_NAME_LABEL.toString());
//        coordinateXLabel.setText(FlatCreatingAndUpdatingFormElements.COORDINATE_X_LABEL.toString());
//        coordinateYLabel.setText(FlatCreatingAndUpdatingFormElements.COORDINATE_Y_LABEL.toString());
//        numberOfParticipantsLabel.setText(FlatCreatingAndUpdatingFormElements.NUMBER_OF_PARTICIPANTS_LABEL.toString());
//        genreLabel.setText(FlatCreatingAndUpdatingFormElements.GENRE_LABEL.toString());
//        personNameLabel.setText(FlatCreatingAndUpdatingFormElements.PERSON_NAME_LABEL.toString());
//        personHeightLabel.setText(FlatCreatingAndUpdatingFormElements.PERSON_HEIGHT_LABEL.toString());
//        personNationalityLabel.setText(FlatCreatingAndUpdatingFormElements.PERSON_NATIONALITY_LABEL.toString());
//        locationXLabel.setText(FlatCreatingAndUpdatingFormElements.LOCATION_X_LABEL.toString());
//        locationYLabel.setText(FlatCreatingAndUpdatingFormElements.LOCATION_Y_LABEL.toString());
//        locationZLabel.setText(FlatCreatingAndUpdatingFormElements.LOCATION_Z_LABEL.toString());
//
//        okButton.setText(FlatCreatingAndUpdatingFormElements.OK_Button.toString());
//        cancelButton.setText(FlatCreatingAndUpdatingFormElements.CANCEL_BUTTON.toString());
    }

    private void preValidation() {
//        FlatFieldsValidators.bandNameValidate(bandNameTextField, this);
//        FlatFieldsValidators.coordinateXValidate(coordinateXTextField, this);
//        FlatFieldsValidators.coordinateYValidate(coordinateYTextField, this);
//        FlatFieldsValidators.numberOfParticipantsValidate(numberOfParticipantsTextField, this);
//        FlatFieldsValidators.genreValidate(genreComboBox, this);
//        FlatFieldsValidators.personNameValidate(personNameTextField, this);
//        FlatFieldsValidators.personHeightValidate(personHeightTextField, this);
//        FlatFieldsValidators.personNationalityValidate(personNationalityComboBox, this);
//        FlatFieldsValidators.locationXValidate(locationXTextField, this);
//        FlatFieldsValidators.locationYValidate(locationYTextField, this);
//        FlatFieldsValidators.locationZValidate(locationZTextField, this);

    }

    private void initListeners() {
//        bandNameTextField.textProperty().addListener(change-> FlatFieldsValidators.bandNameValidate(bandNameTextField, this));
//        coordinateXTextField.textProperty().addListener(change->FlatFieldsValidators.coordinateXValidate(coordinateXTextField, this));
//        coordinateYTextField.textProperty().addListener(change->FlatFieldsValidators.coordinateYValidate(coordinateYTextField, this));
//        numberOfParticipantsTextField.textProperty().addListener(change -> FlatFieldsValidators.numberOfParticipantsValidate(numberOfParticipantsTextField, this));
//        genreComboBox.valueProperty().addListener(change->FlatFieldsValidators.genreValidate(genreComboBox, this));
//        personNameTextField.textProperty().addListener(change->FlatFieldsValidators.personNameValidate(personNameTextField, this));
//        personHeightTextField.textProperty().addListener(change -> FlatFieldsValidators.personHeightValidate(personHeightTextField, this));
//        personNationalityComboBox.valueProperty().addListener(change -> FlatFieldsValidators.personNationalityValidate(personNationalityComboBox, this));
//        locationXTextField.textProperty().addListener(change->FlatFieldsValidators.locationXValidate(locationXTextField, this));
//        locationYTextField.textProperty().addListener(change->FlatFieldsValidators.locationYValidate(locationYTextField, this));
//        locationZTextField.textProperty().addListener(change->FlatFieldsValidators.locationZValidate(locationZTextField, this));

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
//        Notifications.create().text(RuntimeOutputs.FIELDS_DOES_NOT_VALID.toString()).position(Pos.TOP_CENTER).show();
        currentStage.close();
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
//        return isBandNameValidity && isCoordinateXValidity && isCoordinateYValidity
//                && isGenreValidity && isLocationYValidity && isLocationXValidity
//                && isLocationZValidity && isNumberOfParticipantsValidity && isPersonNameValidity
//                && isPersonHeightValidity;
        return true;
    }
//
//    public void setBandNameValidity(boolean bandNameValidity) {
//        isBandNameValidity = bandNameValidity;
//    }
//
//    public void setCoordinateXValidity(boolean coordinateXValidity) {
//        isCoordinateXValidity = coordinateXValidity;
//    }
//
//    public void setCoordinateYValidity(boolean coordinateYValidity) {
//        isCoordinateYValidity = coordinateYValidity;
//    }
//
//    public void setNumberOfParticipantsValidity(boolean numberOfParticipantsValidity) {
//        isNumberOfParticipantsValidity = numberOfParticipantsValidity;
//    }
//
//    public void setGenreValidity(boolean genreValidity) {
//        isGenreValidity = genreValidity;
//    }
//
//    public void setPersonNameValidity(boolean personNameValidity) {
//        isPersonNameValidity = personNameValidity;
//    }
//
//    public void setPersonHeightValidity(boolean personHeightValidity) {
//        isPersonHeightValidity = personHeightValidity;
//    }
//
//    public void setPersonNationalityValidity(boolean personNationalityValidity) {
////        isPersonNationalityValidity = personNationalityValidity;
//    }
//
//    public void setLocationXValidity(boolean locationXValidity) {
//        isLocationXValidity = locationXValidity;
//    }
//
//    public void setLocationYValidity(boolean locationYValidity) {
//        isLocationYValidity = locationYValidity;
//    }
//
//    public void setLocationZValidity(boolean locationZValidity) {
//        isLocationZValidity = locationZValidity;
//    }

    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    public Flat getFlat() {
        return flat;
    }
}
