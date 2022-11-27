package com.jruchel.caloriecounter.validation;

public interface Validator<T> {
    void validate(T object);
}
