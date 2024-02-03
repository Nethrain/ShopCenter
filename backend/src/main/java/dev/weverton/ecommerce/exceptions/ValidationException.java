package dev.weverton.ecommerce.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationException extends StandardException{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationException(Integer status, String msg, Instant timestamp, String path) {
        super(status, msg, timestamp, path);
    }

    public ValidationException() {
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String field, String message) {
        this.errors.add(new FieldMessage(field, message));
    }
}
