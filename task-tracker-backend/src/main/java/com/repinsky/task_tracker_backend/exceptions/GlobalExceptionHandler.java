package com.repinsky.task_tracker_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.repinsky.task_tracker_backend.constants.InfoMessage.*;

@ControllerAdvice
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

    @ExceptionHandler
    public ResponseEntity<AppError> handleUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(new AppError(USER_NOT_FOUND_CODE.getValue(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleTaskNotFoundException(TaskNotFoundException e) {
        return new ResponseEntity<>(new AppError(TASK_NOT_FOUND_CODE.getValue(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleBadRequestException(BadRequestException e){
        return new ResponseEntity<>(new AppError(BAD_REQUEST_ERROR_CODE.getValue(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
