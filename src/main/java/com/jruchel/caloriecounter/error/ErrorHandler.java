package com.jruchel.caloriecounter.error;

import com.deepl.api.DeepLException;
import com.jruchel.caloriecounter.error.exceptions.ApplicationException;
import com.jruchel.caloriecounter.model.api.ErrorResponse;
import java.util.Date;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
public class ErrorHandler {

    @Order(0)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleDefaultErrors(Throwable throwable) {
        return ResponseEntity.internalServerError()
                .body(
                        ErrorResponse.builder()
                                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message("Unexpected error occurred")
                                .build());
    }

    @ExceptionHandler(DeepLException.class)
    public ResponseEntity<ErrorResponse> deepLException(DeepLException deepLException) {
        return createResponseEntity(
                fromException(deepLException, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> nullPointerException(
            NullPointerException nullPointerException) {
        if (nullPointerException.getMessage() == null
                || nullPointerException.getMessage().isBlank())
            nullPointerException = new NullPointerException("Resource not found");
        return createResponseEntity(fromException(nullPointerException, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationException(
            ApplicationException applicationException) {
        return createResponseEntity(applicationException.toErrorResponse());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> validationException(
            ValidationException validationException) {
        return createResponseEntity(
                new ErrorResponse(
                        validationException.getMessage(), new Date(), HttpStatus.CONFLICT));
    }

    private ErrorResponse fromException(Exception e, HttpStatus httpStatus) {
        return new ErrorResponse(e.getMessage(), new Date(), httpStatus);
    }

    private ResponseEntity<ErrorResponse> createResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
    }
}
