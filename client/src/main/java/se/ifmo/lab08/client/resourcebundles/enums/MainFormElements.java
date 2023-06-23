package se.ifmo.lab08.client.resourcebundles.enums;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum MainFormElements {
    SETTINGS_MENU("settingsMenu"),
    LOG_OUT_MENU_ITEM("logOutMenuItem"),
    LANGUAGE_MENU_ITEM("languageMenuItem"),
    INFO_MENU("infoMenu"),
    CREATE_FILTER_BUTTON("createFilterButton"),
    REMOVE_FILTERS_BUTTON("removeFiltersButton"),
    ADD_BUTTON("addButton"),
    UPDATE_BUTTON("updateButton"),
    REMOVE_BY_ID_BUTTON("removeByIdButton"),
    REMOVE_LAST_BUTTON("removeLastButton"),
    REMOVE_BY_FURNISH_BUTTON("removeByFurnishButton"),
    SHUFFLE_BUTTON("shuffleButton"),
    CLEAR_BUTTON("clearButton"),
    CONTROLLERS_LABEL("controllersLabel"),
    EXECUTE_SCRIPT_BUTTON("executeScriptButton"),
    VISUALIZE_BUTTON("visualizeButton"),
    USER_INFO_LABEL("userInfoLabel"),
    USER_BUTTON("userButton");
    private final String bundleObjectName;

    MainFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.mainform.MainForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
