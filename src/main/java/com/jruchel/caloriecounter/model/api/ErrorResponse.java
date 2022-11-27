package com.jruchel.caloriecounter.model.api;

import java.util.Date;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String message;
    @Builder.Default private Date date = new Date();
    private HttpStatus httpStatus;
}
