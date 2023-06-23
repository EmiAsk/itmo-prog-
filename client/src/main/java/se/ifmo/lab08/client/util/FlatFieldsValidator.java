package se.ifmo.lab08.client.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import se.ifmo.lab08.client.controller.FlatCreatingAndUpdatingFormController;
import se.ifmo.lab08.common.entity.Furnish;
import se.ifmo.lab08.common.entity.Transport;
import se.ifmo.lab08.common.entity.View;

public class FlatFieldsValidator {
    private static final long LIMIT_X = 540;
    private static final float LIMIT_Y = 498;
    private static final int AREA_LIMIT = 715;
    private static final int HOUSE_YEAR = 636;


    public static boolean flatNameValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        String value = textField.getText();
        if (value.isBlank()) {
            setWrongValueTextFieldStyle(textField);
            controller.setFlatNameValidity(false);
            return false;
        }
        setAcceptedValueTextFieldStyle(textField);
        controller.setFlatNameValidity(true);
        return true;
    }

    public static boolean flatIdValidate(String value) {
        try {
            long id = Long.parseLong(value);
            return id > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static boolean coordinateXValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (!textField.getText().isBlank() && coordinateXCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            controller.setCoordinateXValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setCoordinateXValidity(false);
        return false;
    }

    public static boolean coordinateYValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (!textField.getText().isBlank() && coordinateYCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            controller.setCoordinateYValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setCoordinateYValidity(false);
        return false;
    }


    public static boolean coordinateXValidate(TextField textField) {
        if (!textField.getText().isBlank() && coordinateXCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        return false;
    }

    public static boolean coordinateYValidate(TextField textField) {
        if (!textField.getText().isBlank() && coordinateYCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        return false;
    }

    public static boolean numberOfRoomsValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (numberOfRoomsCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            controller.setNumberOfRoomsValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setNumberOfRoomsValidity(false);
        return false;
    }

    public static boolean furnishValidate(ComboBox<Furnish> comboBox, FlatCreatingAndUpdatingFormController controller) {
        setAcceptedValueTextFieldStyle(comboBox);
        controller.setFurnishValidity(true);
        return true;
    }

    public static boolean viewValidate(ComboBox<View> comboBox, FlatCreatingAndUpdatingFormController controller) {
        if (comboBox.getValue() == null) {
            setWrongValueTextFieldStyle(comboBox);
            controller.setViewValidity(false);
            return false;
        }
        setAcceptedValueTextFieldStyle(comboBox);
        controller.setViewValidity(true);
        return true;
    }

    public static boolean transportValidate(ComboBox<Transport> comboBox, FlatCreatingAndUpdatingFormController controller) {
        setAcceptedValueTextFieldStyle(comboBox);
        controller.setTransportValidity(true);
        return true;
    }

    public static boolean areaValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (areaCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            controller.setAreaValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setAreaValidity(false);
        return false;
    }

    public static boolean houseNameValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (textField.getText().isBlank()) {
            setWrongValueTextFieldStyle(textField);
            controller.setHouseNameValidity(false);
            return false;
        }
        setAcceptedValueTextFieldStyle(textField);
        controller.setHouseNameValidity(true);
        return true;
    }

    public static boolean houseYearValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (houseYearCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            controller.setHouseYearValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setHouseYearValidity(false);
        return false;
    }

    public static boolean houseNumberOfFlatsValidate(TextField textField, FlatCreatingAndUpdatingFormController controller) {
        if (houseNumberOfFlatsCheck(textField.getText())) {
            setAcceptedValueTextFieldStyle(textField);
            controller.setHouseNumberOfFlatsValidity(true);
            return true;
        }
        setWrongValueTextFieldStyle(textField);
        controller.setHouseNumberOfFlatsValidity(false);
        return false;
    }

    private static boolean coordinateXCheck(String x) {
        try {
            return Long.parseLong(x) <= LIMIT_X && Long.parseLong(x) >= 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean coordinateYCheck(String y) {
        y = y.replace(',', '.');
        try {
            return Float.parseFloat(y) <= LIMIT_Y && Float.parseFloat(y) >= 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean numberOfRoomsCheck(String value) {
        try {
            value = value.replace(",", ".");
            return Long.parseLong(value) > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public static boolean areaCheck(String height) {
        height = height.replace(',', '.');
        try {
            return Long.parseLong(height) > 0 && Long.parseLong(height) <= AREA_LIMIT;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean houseYearCheck(String value) {
        try {
            return Long.parseLong(value) > 0 && Long.parseLong(value) <= HOUSE_YEAR;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean houseNumberOfFlatsCheck(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static void setWrongValueTextFieldStyle(TextField textField) {
        textField.setStyle("""
                -fx-border-radius: 100;
                -fx-background-radius: 100;
                -fx-border-width: 2;
                -fx-border-color: red;
                """);
    }

    private static void setAcceptedValueTextFieldStyle(TextField textField) {
        textField.setStyle("""
                -fx-border-radius: 100;
                -fx-background-radius: 100;
                -fx-border-width: 2;
                -fx-border-color: green;
                """);
    }

    private static void setWrongValueTextFieldStyle(ComboBox comboBox) {
        comboBox.setStyle("""
                -fx-border-radius: 100;
                -fx-background-radius: 100;
                -fx-border-width: 2;
                -fx-border-color: red;
                """);
    }

    private static void setAcceptedValueTextFieldStyle(ComboBox comboBox) {
        comboBox.setStyle("""
                -fx-border-radius: 100;
                -fx-background-radius: 100;
                -fx-border-width: 2;
                -fx-border-color: green;
                """);
    }
}
