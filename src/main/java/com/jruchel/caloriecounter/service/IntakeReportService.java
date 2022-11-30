package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.model.internal.DailyIntakeReport;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.DailyIntakeReportRepository;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntakeReportService {

    private final MealService mealService;
    private final UserService userService;
    private final DailyIntakeReportRepository dailyIntakeReportRepository;

    public DailyIntakeReport generateDailyIntakeReport(final String username, Date date) {
        User user = userService.findByUsername(username);
        List<Meal> meals = mealService.getMealsByDayByUser(username, date);
        int caloriesConsumed = sumDailyCalories(meals);
        int leftToConsume = user.getDailyLimit() - caloriesConsumed;
        int dailyLimit = user.getDailyLimit();
        boolean dailyLimitReached = isDailyLimitReached(dailyLimit, caloriesConsumed);
        return new DailyIntakeReport(
                UUID.randomUUID().toString(),
                username,
                DateUtils.removeTime(new Date()),
                user.getDailyLimit(),
                sumDailyCalories(meals),
                meals,
                leftToConsume,
                dailyLimitReached,
                dailyLimitReached && isDailyLimitExceeded(dailyLimit, caloriesConsumed));
    }

    public DailyIntakeReport saveDailyIntakeReport(DailyIntakeReport dailyIntakeReport) {
        return dailyIntakeReportRepository.save(dailyIntakeReport);
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
            sum += m.getCaloriesSum();
        }
        return sum;
    }
}
