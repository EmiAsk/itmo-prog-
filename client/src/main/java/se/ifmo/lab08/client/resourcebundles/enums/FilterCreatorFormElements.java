package se.ifmo.lab08.client.resourcebundles.enums;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum FilterCreatorFormElements {
    FILTER_COLUMN_LABEL("filterColumnLabel"),
    SIGN_LABEL("signLabel"),
    VALUE_FOR_FILTERING_LABEL("valueForFilteringLabel"),
    COLUMNS_FOR_FILTERING_COMBO_BOX("columnsForFilteringComboBox"),
    SIGNS_COMBO_BOX("signsCombobox"),
    FILTERING_VALUE_TEXT_FIELD("filteringValueTextField"),
    CREATE_BUTTON("createButton"),
    CANCEL_BUTTON("cancelButton");
    private final String bundleObjectName;

    FilterCreatorFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.filtercreatorform.FilterCreatorForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
