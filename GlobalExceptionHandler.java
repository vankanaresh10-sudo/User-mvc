package com.example.securemvc.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Validation failed",
            "message", "One or more fields are invalid"
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<?> constraint(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Validation failed"
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> illegal(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "error", ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception ex) {
        // Do not expose stack traces, SQL, secrets, or internal implementation details.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "error", "Internal server error"
        ));
    }
}
