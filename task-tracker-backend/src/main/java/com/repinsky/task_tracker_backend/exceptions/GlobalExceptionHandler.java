package com.repinsky.task_tracker_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.repinsky.task_tracker_backend.constants.InfoMessage.INCORRECT_LOGIN_OR_PASSWORD;
import static com.repinsky.task_tracker_backend.constants.InfoMessage.RESOURCE_NOT_FOUND_CODE;

public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError(RESOURCE_NOT_FOUND_CODE.getValue(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(new AppError(INCORRECT_LOGIN_OR_PASSWORD.getValue(), e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }
}
