package com.jruchel.caloriecounter.error.exceptions;

import org.springframework.http.HttpStatus;

public class FieldValueValidationException extends ApplicationException {

    public FieldValueValidationException(
            String objectName, String fieldName, String fieldValue, String message) {
        super(createMessage(objectName, fieldName, fieldValue, message), HttpStatus.CONFLICT);
    }

    private static String createMessage(
            String objectName, String fieldName, String fieldValue, String message) {
        return "Failed to validate "
                + objectName
                + ".\n"
                + "Invalid value for field "
                + fieldName
                + "="
                + fieldValue
                + ".\n"
                + message
                + ".";
    }
}
