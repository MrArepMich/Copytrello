package com.repinsky.task_tracker_backend.constants;

public enum InfoMessage {
    RESOURCE_NOT_FOUND_CODE("RESOURCE_NOT_FOUND"),
    CHECK_USERNAME_PASSWORD_ERROR_CODE("CHECK_USERNAME_PASSWORD_ERROR"),
    USER_NOT_FOUND_CODE("USER_NOT_FOUND"),
    TASK_NOT_FOUND_CODE("TASK_NOT_FOUND"),
    BAD_REQUEST_ERROR_CODE("BAD_REQUEST_ERROR"),
    INPUT_DATA_ERROR_CODE("INPUT_DATA_ERROR"),

    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    PASSWORD_CANNOT_BE_EMPTY("Password cannot be empty"),
    INVALID_PASSWORD_CHARACTERS("Invalid password characters. Latin letters A-Z, a-z and numbers 0-9 are acceptable"),
    PASSWORD_MISMATCH("Password mismatch"),
    EMAIL_CANNOT_BE_EMPTY("Email cannot be empty"),
    INVALID_EMAIL_CHARACTERS("Invalid email characters"),
    INCORRECT_EMAIL("Incorrect email"),
    LOGIN_CANNOT_BE_EMPTY("Login cannot be empty"),
    INVALID_LOGIN_CHARACTERS("Invalid login characters. Latin letters A-Z, a-z and numbers 0-9 are acceptable"),
    EMAIL_ALREADY_EXISTS("This email already exists in the system");

    private final String value;

    InfoMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
