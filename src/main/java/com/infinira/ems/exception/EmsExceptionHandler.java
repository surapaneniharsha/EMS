package com.infinira.ems.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestControllerAdvice // Use this for REST APIs
public class EmsExceptionHandler {

    @ExceptionHandler(EmsException.class)
    public ResponseEntity<String> handleEmsException(EmsException emsEx) {

        return new ResponseEntity<>(emsEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Pro-Tip: Add a generic handler to catch unexpected bugs
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("An internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}