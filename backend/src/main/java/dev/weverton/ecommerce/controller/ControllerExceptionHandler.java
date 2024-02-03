package dev.weverton.ecommerce.controller;

import dev.weverton.ecommerce.exceptions.DataIntegrityException;
import dev.weverton.ecommerce.exceptions.EntityNotFoundException;
import dev.weverton.ecommerce.exceptions.StandardException;
import dev.weverton.ecommerce.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardException> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandardException err = new StandardException(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                Instant.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardException> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
        StandardException err = new StandardException(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                Instant.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationException> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationException err = new ValidationException(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                Instant.now(),
                request.getRequestURI()
        );

        for(FieldError fErr : e.getBindingResult().getFieldErrors()){
            err.addError(fErr.getField(), fErr.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
