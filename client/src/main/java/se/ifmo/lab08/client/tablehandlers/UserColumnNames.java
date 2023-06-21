package se.ifmo.lab08.client.tablehandlers;


public enum UserColumnNames {
    USER_ID_COLUMN("id"),
    USER_USERNAME_COLUMN("username"),
    USER_ROLE_COLUMN("role");

    private final String bundleObjectName;

    UserColumnNames(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.musicbandfieldsbundles.FlatFieldsRB", MainFormController.getCurrentLocale().get().getLocale());
//        return resourceBundle.getString(bundleObjectName);
        return bundleObjectName;
    }
}
