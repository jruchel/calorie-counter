package com.jruchel.caloriecounter.validator;

import com.jruchel.caloriecounter.model.internal.Meal;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealValidator {

    public void validate(Meal validatedObject, List<Meal> usersDailyMeals) {
        checkMealNameUnique(validatedObject, usersDailyMeals);
    }

    private void checkMealNameUnique(Meal validatedObject, List<Meal> usersDailyMeals) {
        List<String> dailyMealNames =
                usersDailyMeals.stream().map(Meal::getName).collect(Collectors.toList());
        if (dailyMealNames.contains(validatedObject.getName()))
            throw new ValidationException("Meal name must be unique for the day");
    }
}
