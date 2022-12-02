package com.jruchel.caloriecounter.error;

import org.springframework.http.HttpStatus;

public class NutritionInformationNotFound extends ApplicationException {

    public NutritionInformationNotFound(String item) {
        super(
                "Nutrition information not found for '%s', please input it manually"
                        .formatted(item),
                HttpStatus.NOT_FOUND);
    }
}
