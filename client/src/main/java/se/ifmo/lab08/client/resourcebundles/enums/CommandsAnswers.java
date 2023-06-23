package se.ifmo.lab08.client.resourcebundles.enums;

import se.ifmo.lab08.client.controller.MainFormController;

import java.util.ResourceBundle;

public enum CommandsAnswers {
    ID_DOES_NOT_EXIST("idDoesNotExist"),
    INFO_EXECUTED("infoCommandExecuted"),
    ADD_IF_MIN_COMMAND_ID_IS_NOT_MIN("addIfMinCommandIdIsNotMin"),
    ADD_IF_MIN_COMMAND_ID_IN_WRONG_FORMAT("addIfMinCommandIdInWrongFormat"),
    ADD_COMMAND_EXECUTED("addCommandExecuted"),
    ADD_COMMAND_NOT_EXECUTED("addCommandNotExecuted"),
    ADD_IF_MIN_COMMAND_EXECUTED("addIfMinCommandExecuted"),
    ADD_IF_MIN_COMMAND_NOT_EXECUTED("addIfMinCommandNotExecuted"),
    CLEAR_COMMAND_EXECUTED("clearCommandExecuted"),
    EXECUTE_SCRIPT_COMMAND_EXECUTED("executeScriptCommandExecuted"),
    REMOVE_BY_ID_COMMAND_EXECUTED("removeByIdCommandExecuted"),
    REMOVE_BY_ID_COMMAND_NOT_EXECUTED("removeByIdCommandNotExecuted"),
    SIGN_IN_COMMAND_EXECUTED("signInCommandExecuted"),
    SIGN_IN_COMMAND_NOT_EXECUTED("signInCommandNotExecuted"),
    SIGN_UP_COMMAND_EXECUTED("signUpCommandExecuted"),
    SIGN_UP_COMMAND_NOT_EXECUTED("signUpCommandNotExecuted"),
    UPDATE_COMMAND_EXECUTED("updateCommandExecuted"),
    UPDATE_COMMAND_NOT_EXECUTED("updateCommandNotExecuted"),
    EXECUTE_SCRIPT_COMMAND_RECURSION_DETECTED("executeScriptRecursionDetected"),
    EXECUTE_SCRIPT_COMMAND_CAN_NOT_ACCESS_TO_FILE("executeScriptCanNotAccessToFile"),
    EXECUTE_SCRIPT_CAN_NOT_FOUND_FILE("executeScriptCanNotFoundFile"),
    COUNT_GREATER_THAN_FRONT_MAN_EXECUTED("countGreaterThanFrontManExecuted"),
    COUNT_GREATER_THAN_FRONT_MAN_NOT_EXECUTED("countGreaterThanFrontManNotExecuted"),
    GROUP_COUNTING_BY_COORDINATES_EXECUTED("groupCountingByCoordinatesExecuted"),
    COLLECTION_IS_EMPTY("collectionIsEmpty");

    private final String bundleObjectName;

    CommandsAnswers(String bundleObjectName) {
        this.bundleObjectName = bundleObjectName;
    }

    @Override
    public String toString() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("client.UI.resourcebundles.commandsanswersbundles.CommandsAnswersRB", MainFormController.getCurrentLocale().get().getLocale());
        return resourceBundle.getString(bundleObjectName);
    }
}
