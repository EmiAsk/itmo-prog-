package se.ifmo.lab08.client.resourcebundles.enums;

import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum ChangeRoleFormElements {
    ROLE_LABEL("roleLabel"),
    ROLE_COMBOBOX("roleComboBox"),
    OK_BUTTON("okButton"),
    CANCEL_BUTTON("cancelButton");
    private final String bundleObjectName;

    ChangeRoleFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.changeroleform.ChangeRoleForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
