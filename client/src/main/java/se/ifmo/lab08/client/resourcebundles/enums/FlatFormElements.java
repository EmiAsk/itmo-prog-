package se.ifmo.lab08.client.resourcebundles.enums;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum FlatFormElements {

    FLAT_NAME_TEXT_FIELD("flatNameTextField"),
    COORDINATE_X_TEXT_FIELD("coordinateXTextField"),
    COORDINATE_Y_TEXT_FIELD("coordinateYTextField"),
    AREA_TEXT_FIELD("areaTextField"),
    NUMBER_OF_ROOMS_TEXT_FIELD("numberOfRoomsTextField"),
    FURNISH_COMBO_BOX("furnishComboBox"),
    VIEW_COMBO_BOX("viewComboBox"),
    TRANSPORT_COMBO_BOX("transportComboBox"),
    HOUSE_NAME_TEXT_FIELD("houseNameTextField"),
    HOUSE_YEAR_TEXT_FIELD("houseYearTextField"),
    HOUSE_NUMBER_OF_FLATS_TEXT_FIELD("houseNumberOfFlatsTextField"),

    FLAT_NAME_LABEL("flatNameLabel"),
    COORDINATE_X_LABEL("coordinateXLabel"),
    COORDINATE_Y_LABEL("coordinateYLabel"),
    AREA_LABEL("areaLabel"),
    NUMBER_OF_ROOMS_LABEL("numberOfRoomsLabel"),
    FURNISH_LABEL("furnishLabel"),
    VIEW_LABEL("viewLabel"),
    TRANSPORT_LABEL("transportLabel"),
    HOUSE_NAME_LABEL("houseNameLabel"),
    HOUSE_YEAR_LABEL("houseYearLabel"),
    HOUSE_NUMBER_OF_FLATS_LABEL("houseNumberOfFlatsLabel"),

    INVALID_VALUE("invalidValue"),

    OK_Button("okButton"),
    CANCEL_BUTTON("cancelButton");

    private final String bundleObjectName;

    FlatFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.flatcreatingform.FlatCreatingForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
