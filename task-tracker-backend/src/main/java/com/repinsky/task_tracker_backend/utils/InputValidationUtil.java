package com.repinsky.task_tracker_backend.utils;

import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

import static com.repinsky.task_tracker_backend.constants.InfoMessage.*;

public class InputValidationUtil {
    private static final Pattern EMAIL_ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9@._-]+$");
    private static final Pattern PASSWORD_ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");

    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 32;


    public InputValidationUtil() {
    }

    public String acceptableEmail(String email) {
        if (email == null || email.isBlank()) return EMAIL_CANNOT_BE_EMPTY.getValue();
        if (!EMAIL_ALLOWED_PATTERN.matcher(email).matches()) {
            return INVALID_EMAIL_CHARACTERS.getValue();
        }
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email) ? "" : INCORRECT_EMAIL.getValue();
    }

    public String acceptablePassword(String password) {
        if (password == null || password.isBlank()) return PASSWORD_CANNOT_BE_EMPTY.getValue();
        if (password.length() < MIN_PASSWORD_LENGTH)
            return "The minimum password length must be " + MIN_PASSWORD_LENGTH + " symbols";
        if (password.length() > MAX_PASSWORD_LENGTH)
            return "The maximum password length must be " + MAX_PASSWORD_LENGTH + " symbols";
        if (!PASSWORD_ALLOWED_PATTERN.matcher(password).matches()) {
            return INVALID_PASSWORD_CHARACTERS.getValue();
        }
        return "";
    }
}
