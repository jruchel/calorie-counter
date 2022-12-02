package com.jruchel.caloriecounter.error;

import com.jruchel.caloriecounter.model.api.ErrorResponse;
import java.util.Date;
import org.springframework.http.HttpStatus;

public class ApplicationException extends Exception {

    private String message;
    private HttpStatus httpStatus;

    public ApplicationException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(message, new Date(), httpStatus);
    }
}
