package se.ifmo.lab08.client.resourcebundles.enums;

import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum UserFormElements {
    COMMAND_LABEL("commandLabel"),
    CHANGE_ROLE_BUTTON("changeRoleButton"),
    BACK_TO_TABLE_BUTTON("backToTableButton");
    private final String bundleObjectName;

    UserFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.userform.UserForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
