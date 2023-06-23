package se.ifmo.lab08.client.tablehandlers;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum FlatColumnNames {
    ID_COLUMN("id"),
    NAME_COLUMN("name"),
    COORDINATES_X_COLUMN("coordinateX"),
    COORDINATES_Y_COLUMN("coordinateY"),
    CREATION_DATE_COLUMN("creationDate"),
    AREA_COLUMN("area"),
    NUMBER_OF_ROOMS("numberOfRooms"),
    FURNISH_COLUMN("furnish"),
    VIEW_COLUMN("view"),
    TRANSPORT_COLUMN("transport"),
    HOUSE_NAME_COLUMN("houseName"),
    HOUSE_YEAR_COLUMN("houseYear"),
    HOUSE_NUMBER_OF_FLATS_COLUMN("houseNumberOfFlats"),
    OWNER_USERNAME_COLUMN("ownerUsername");

    private final String bundleObjectName;

    FlatColumnNames(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.flatfield.FlatField", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
//        return bundleObjectName;
    }
}
