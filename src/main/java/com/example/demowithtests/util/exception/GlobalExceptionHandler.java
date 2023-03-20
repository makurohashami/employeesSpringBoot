package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.mail.AuthenticationFailedException;
import javax.mail.SendFailedException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Resource not found",
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Backend error. Class: " + ex.getClass().getName() + " Message: " + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotVisibleException.class)
    public ResponseEntity<?> resourceNotVisibleException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Resource not visible",
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Validation failed. Field: " + ex.getBindingResult().getFieldError().getField() + " Message: " + ex.getBindingResult().getFieldError().getDefaultMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MailException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Mail failed. " + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> methodAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Mail authentication failed. " + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
