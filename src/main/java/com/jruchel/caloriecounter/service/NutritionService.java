package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.error.exceptions.NutritionInformationNotFound;
import com.jruchel.caloriecounter.service.nutritionix.NutritionixClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutritionService {

    private final NutritionixClient nutritionixClient;

    public int getCalories(String foodItem) throws NutritionInformationNotFound {
        double calories =
                nutritionixClient
                        .getNutrients(foodItem)
                        .orElseThrow(() -> new NutritionInformationNotFound(foodItem))
                        .getFoods()
                        .get(0)
                        .getCalories();
        if (calories <= 0) throw new NutritionInformationNotFound(foodItem);
        return (int) calories;
    }
}
