package com.jruchel.caloriecounter.error;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String username) {
        super("Could not find user '%s'".formatted(username), HttpStatus.NOT_FOUND);
    }
}
