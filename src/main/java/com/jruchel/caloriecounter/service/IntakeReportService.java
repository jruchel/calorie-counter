package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.error.UserNotFoundException;
import com.jruchel.caloriecounter.mapper.report.IntakeReportMapper;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.model.internal.report.DailyIntakeReport;
import com.jruchel.caloriecounter.model.internal.report.SingleDaySummary;
import com.jruchel.caloriecounter.model.internal.report.WeeklyIntakeReport;
import com.jruchel.caloriecounter.repository.DailyIntakeReportRepository;
import com.jruchel.caloriecounter.utils.DateUtils;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntakeReportService {

    private final MealService mealService;
    private final UserService userService;
    private final DailyIntakeReportRepository dailyIntakeReportRepository;
    private final IntakeReportMapper intakeReportMapper;

    public DailyIntakeReport generateDailyIntakeReport(final String username)
            throws UserNotFoundException {
        User user = userService.findByUsername(username);
        List<Meal> meals = mealService.getTodaysMealsForUser(username);
        return new DailyIntakeReport(
                UUID.randomUUID().toString(),
                user.getId(),
                DateUtils.removeTime(new Date()),
                user.getDailyLimit(),
                meals);
    }

    public WeeklyIntakeReport generateWeeklyIntakeReport(final String username, Date date)
            throws UserNotFoundException {
        User user = userService.findByUsername(username);
        List<Date> weekDays = DateUtils.getNonFutureDates(DateUtils.getWeekDays(date));
        weekDays.remove(weekDays.size() - 1);
        Map<String, SingleDaySummary> weekdaySummaries = new LinkedHashMap<>();
        SingleDaySummary todaysSummary =
                intakeReportMapper.toSingleDaySummary(generateDailyIntakeReport(username));

        for (Date d : weekDays) {
            String day = DateUtils.getDayOfTheWeek(d);
            SingleDaySummary daySummary =
                    intakeReportMapper.toSingleDaySummary(getDailyIntakeReport(username, d));
            if (daySummary != null) weekdaySummaries.put(day, daySummary);
        }

        weekdaySummaries.put(DateUtils.getDayOfTheWeek(todaysSummary.getDate()), todaysSummary);

        return new WeeklyIntakeReport(UUID.randomUUID().toString(), user.getId(), weekdaySummaries);
    }

    public DailyIntakeReport getDailyIntakeReport(final String username, final Date date)
            throws UserNotFoundException {
        User user = userService.findByUsername(username);
        return dailyIntakeReportRepository.findDailyReportByUserAndDay(user.getId(), date);
    }

    public DailyIntakeReport saveDailyIntakeReport(DailyIntakeReport dailyIntakeReport) {
        return dailyIntakeReportRepository.save(dailyIntakeReport);
    }
}
