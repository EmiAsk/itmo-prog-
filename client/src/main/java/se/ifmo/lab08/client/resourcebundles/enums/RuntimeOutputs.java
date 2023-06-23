package se.ifmo.lab08.client.resourcebundles.enums;


import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum RuntimeOutputs {
    SOMETHING_WENT_WRONG("somethingWentWrong"),
    SERVER_DOES_NOT_RESPOND("serverDoesNotRespond"),
    LOGOUT_SUCCESSFULLY("logoutSuccessfully"),
    LOGIN_SUCCESSFULLY("loginSuccessfully"),
    FAILED_LOGIN("failedLogin"),
    FAILED_SIGNUP("failedSignUp"),
    SIGNUP_SUCCESSFULLY("signUpSuccessfully"),
    VALUE_WAS_NOT_ENTERED("valueWasNotEntered"),
    SIGN_WAS_NOT_SELECTED("signWasNotSelected"),
    COLUMN_WAS_NOT_SELECTED("columnWasNotSelected"),
    FLAT_WAS_NOT_SELECTED_IN_TABLE("flatWasNotSelectedInTable"),
    USER_WAS_NOT_SELECTED_IN_TABLE("userWasNotSelectedInTable"),
    CHANGE_OWN_ROLE("changeOwnRole"),
    FIELDS_NOT_VALID("fieldsNotValid"),
    INTERRUPTED_INPUT("interruptedInput"),
    ENTER_COMMAND("enterCommand"),
    MANAGE_FLATS("manageFlats"),
    COMMAND_NOT_FOUND("commandNotFound"),
    COMMAND_EXECUTED("commandExecuted"),
    FAILED_TO_LOAD_DATA("failedToLoadData"),
    FAILED_TO_EXECUTE_COMMAND("failedToExecuteCommand"),
    INVALID_ARGS("invalidArgs"),
    RECURSION_ERROR("recursionError"),
    END_OF_SCRIPT("endOfScript"),
    FILE_LISTENER_CAN_NOT_READ_FILE("fileListenerCanNotReadFromFile"),
    FILE_LISTENER_PROBLEM_WITH_SCRIPT_FILE("fileListenerProblemWithScriptFile"),
    CONNECTION_UNKNOWN_HOST_NAME("connectionUnknownHostName"),
    CONNECTION_UNKNOWN_CLASS_RECEIVED("connectionUnknownClassReceived"),
    CONNECTION_COULD_NOT_BE_ESTABLISHED("connectionCouldNotBeEstablished"),
    CONNECTION_RECONNECTING("connectionReconnecting"),
    CONNECTION_CAN_NOT_CLOSE_CHANNEL("canNotCloseChannelWithServer"),
    CONNECTION_RECONNECTED("connectionReconnected"),
    CONNECTION_RECONNECTION_FAILED("connectionReconnectionFailed"),
    TCP_SENDER_CAN_NOT_SEND_REQUEST("tcpSenderCanNOtSendRequest"),
    CAN_NOT_INIT_COMPONENT("canNonInitComponent");
    private final String bundleObjectName;

    RuntimeOutputs(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourcebundle.runtimeoutput.RuntimeOutput", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
