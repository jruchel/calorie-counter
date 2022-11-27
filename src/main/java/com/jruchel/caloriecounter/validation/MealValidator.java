package com.jruchel.caloriecounter.validation;

import com.jruchel.caloriecounter.model.internal.Meal;
import jakarta.validation.ValidationException;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MealValidator implements Validator<Meal> {

    public void validate(Meal meal) {
        if (!meal.getFoods().isEmpty()) {
            if (sumMealCalories(meal.getFoods()) != meal.getCalories()) {
                throw new ValidationException(
                        "Individual foods must be empty or match calories exactly");
            }
        }
    }

    private int sumMealCalories(Map<String, Integer> meals) {
        int sum = 0;
        for (String key : meals.keySet()) {
            sum += meals.get(key);
        }
        return sum;
    }
}
