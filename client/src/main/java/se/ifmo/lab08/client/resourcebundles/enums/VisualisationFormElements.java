package se.ifmo.lab08.client.resourcebundles.enums;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum VisualisationFormElements {
    BACK_TO_THE_TABLE_BUTTON("backToTheTableButton"),
    COORDINATE_X_LABEL("coordinateXLabel"),
    COORDINATE_Y_LABEL("coordinateYLabel"),
    PERSON_HEIGHT_LABEL("personHeightLabel");

    private final String bundleObjectName;

    VisualisationFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.visualizationform.VisualizationForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
