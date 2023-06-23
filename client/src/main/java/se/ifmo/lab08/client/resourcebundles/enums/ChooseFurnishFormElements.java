package se.ifmo.lab08.client.resourcebundles.enums;

import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum ChooseFurnishFormElements {
    FURNISH_LABEL("furnishLabel"),
    FURNISH_COMBOBOX("furnishComboBox"),
    OK_BUTTON("okButton"),
    CANCEL_BUTTON("cancelButton");
    private final String bundleObjectName;

    ChooseFurnishFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.choosefurnishform.ChooseFurnishForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
