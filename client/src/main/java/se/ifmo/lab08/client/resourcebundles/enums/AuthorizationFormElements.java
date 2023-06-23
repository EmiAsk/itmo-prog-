package se.ifmo.lab08.client.resourcebundles.enums;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum AuthorizationFormElements {
    LOG_IN_TEXT_FIELD("logInTextField"),
    PASSWORD_TEXT_FIELD("passwordTextField"),
    SETTINGS_MENU("settingsMenu"),
    LANGUAGE_MENU_ITEM("languageMenuItem"),
    LOGIN_LABEL("loginLabel"),
    PASSWORD_LABEL("passwordLabel"),
    SIGN_IN_BUTTON("signInButton"),
    SIGN_UP_BUTTON("signUpButton");
    private final String bundleObjectName;

    AuthorizationFormElements(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.authorizationform.AuthorizationForm", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
