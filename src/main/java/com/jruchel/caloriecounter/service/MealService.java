package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.MealRepository;
import com.jruchel.caloriecounter.validator.MealValidator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService extends AbstractService<Meal> {

    private final MealRepository mealRepository;
    private final UserService userService;
    private final MealValidator mealValidator;

    public Meal addMeal(
            final String username, final String name, final Map<String, Integer> foods) {
        User user = userService.findByUsername(username);
        Meal entry = new Meal(UUID.randomUUID().toString(), user.getId(), name, foods, new Date());
        mealValidator.validate(entry, getTodaysMealsForUser(username));
        return mealRepository.insert(entry);
    }

    public List<Meal> getTodaysMealsForUser(final String username) {
        User user = userService.findByUsername(username);
        return mealRepository.findTodaysMealsByUser(user.getId());
    }

    public Meal deleteMeal(final String username, final String name, final Date date) {
        User user = userService.findByUsername(username);
        Meal meal = mealRepository.findMealByDayAndNameForUser(user.getId(), date, name);
        mealRepository.delete(meal);
        return meal;
    }

    public List<Meal> getMealsByUser(String username) {
        User user = userService.findByUsername(username);
        return mealRepository.findMealsByUser(user.getId());
    }
}
