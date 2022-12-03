package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.error.exceptions.FieldValueValidationException;
import java.lang.reflect.Field;

public abstract class AbstractService<Type> {

    public FieldValueValidationException createFieldValidationException(
            Type object, String fieldName, String message)
            throws NoSuchFieldException, IllegalAccessException {

        Field declaredField = object.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        String fieldValue = declaredField.get(object).toString();

        return new FieldValueValidationException(
                object.getClass().getSimpleName(), fieldName, fieldValue, message);
    }
}
