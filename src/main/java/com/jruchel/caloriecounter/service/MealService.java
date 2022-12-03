package com.jruchel.caloriecounter.service;

import com.deepl.api.DeepLException;
import com.jruchel.caloriecounter.error.exceptions.NutritionInformationNotFound;
import com.jruchel.caloriecounter.error.exceptions.UserNotFoundException;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.MealRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class MealService extends AbstractService<Meal> {

    private final MealRepository mealRepository;
    private final UserService userService;
    private final NutritionService nutritionService;
    private final TranslationService translationService;

    public Meal addMeal(final String username, final String name, Map<String, Integer> foods)
            throws NutritionInformationNotFound, UserNotFoundException, DeepLException,
                    InterruptedException {
        addCaloriesToFoods(foods);
        User user = userService.findByUsername(username);
        Meal entry = mealRepository.findMealByDayAndNameForUser(user.getId(), new Date(), name);
        if (entry != null) {
            entry.addFoods(foods);
        } else {
            entry = new Meal(UUID.randomUUID().toString(), user.getId(), name, foods, new Date());
        }
        return mealRepository.save(entry);
    }

    private void addCaloriesToFoods(Map<String, Integer> foods)
            throws NutritionInformationNotFound, InterruptedException, DeepLException {
        for (String key : foods.keySet()) {
            if (foods.get(key) <= 0) {
                foods.put(key, getCaloriesForFood(key));
            }
        }
    }

    private int getCaloriesForFood(String food)
            throws NutritionInformationNotFound, DeepLException, InterruptedException {
        try {
            return nutritionService.getCalories(translationService.translate(food, "en-US"));
        } catch (HttpClientErrorException clientErrorException) {
            throw new NutritionInformationNotFound(food);
        }
    }

    public List<Meal> getTodaysMealsForUser(final String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        return mealRepository.findTodaysMealsByUser(user.getId());
    }

    public Meal deleteMeal(final String username, final String name, final Date date)
            throws UserNotFoundException {
        User user = userService.findByUsername(username);
        Meal meal = mealRepository.findMealByDayAndNameForUser(user.getId(), date, name);
        mealRepository.delete(meal);
        return meal;
    }

    public List<Meal> getMealsByUser(String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        return mealRepository.findMealsByUser(user.getId());
    }
}
