package com.repinsky.task_tracker_backend.exceptions;

import javax.xml.bind.ValidationException;

public class InputDataException extends ValidationException {
    public InputDataException(String validationMessage) {
        super(validationMessage);
    }
}
