package com.jruchel.caloriecounter.error;

public class FieldValueValidationException extends Exception {

    public FieldValueValidationException(
            String objectName, String fieldName, String fieldValue, String message) {
        super(createMessage(objectName, fieldName, fieldValue, message));
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
