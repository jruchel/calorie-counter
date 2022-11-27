package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.model.internal.DailyIntakeReport;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.MealRepository;
import com.jruchel.caloriecounter.validation.MealValidator;
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
            final String username,
            final String name,
            final int calories,
            final Map<String, Integer> foods) {
        User user = userService.findByUsername(username);
        Meal entry =
                new Meal(
                        UUID.randomUUID().toString(),
                        user.getId(),
                        name,
                        calories,
                        foods,
                        new Date());
        mealValidator.validate(entry);
        return mealRepository.insert(entry);
    }

    public DailyIntakeReport generateDailyIntakeReport(final String username) {
        User user = userService.findByUsername(username);
        List<Meal> meals = getTodaysMealsForUser(username);
        int caloriesConsumed = sumDailyCalories(meals);
        int leftToConsume = user.getDailyLimit() - caloriesConsumed;
        int dailyLimit = user.getDailyLimit();
        boolean dailyLimitReached = isDailyLimitReached(dailyLimit, caloriesConsumed);
        return new DailyIntakeReport(
                username,
                DateUtils.removeTime(new Date()),
                user.getDailyLimit(),
                sumDailyCalories(meals),
                meals,
                leftToConsume,
                dailyLimitReached,
                dailyLimitReached && isDailyLimitExceeded(dailyLimit, caloriesConsumed));
    }

    public List<Meal> getTodaysMealsForUser(final String username) {
        User user = userService.findByUsername(username);
        return mealRepository.findTodaysMealsByUser(user.getId());
    }

    public Meal deleteMeal(final String username, final String name, final Date date) {
        User user = userService.findByUsername(username);
        Meal meal = mealRepository.findMealByDateAndNameForUser(user.getId(), date, name);
        mealRepository.delete(meal);
        return meal;
    }

    public List<Meal> getMealsByUser(String username) {
        User user = userService.findByUsername(username);
        return mealRepository.findMealsByUser(user.getId());
    }

    private boolean isDailyLimitReached(int dailyLimit, int caloriesConsumed) {
        int difference = dailyLimit - caloriesConsumed;
        return difference < (double) 2 / 100 * dailyLimit;
    }

    private boolean isDailyLimitExceeded(int dailyLimit, int caloriesConsumed) {
        int difference = Math.abs(dailyLimit - caloriesConsumed);
        return difference > (double) 2 / 100 * dailyLimit;
    }

    private int sumDailyCalories(List<Meal> meals) {
        int sum = 0;
        for (Meal m : meals) {
            sum += m.getCalories();
        }
        return sum;
    }
}
