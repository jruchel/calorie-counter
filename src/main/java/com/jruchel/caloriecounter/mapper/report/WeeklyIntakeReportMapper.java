package com.jruchel.caloriecounter.mapper.report;

import com.jruchel.caloriecounter.model.api.report.WeeklyIntakeReportDTO;
import com.jruchel.caloriecounter.model.internal.report.SingleDaySummary;
import com.jruchel.caloriecounter.model.internal.report.WeeklyIntakeReport;
import com.jruchel.caloriecounter.service.UserService;
import com.jruchel.caloriecounter.utils.DateUtils;
import com.jruchel.caloriecounter.utils.ReportUtils;
import java.util.ArrayList;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = IntakeReportMapper.class)
public abstract class WeeklyIntakeReportMapper {

    @Autowired private UserService userService;

    @Mapping(target = "username", source = "weeklyIntakeReport", qualifiedByName = "username")
    @Mapping(
            target = "weeklyCalorieLimit",
            source = "weeklyIntakeReport",
            qualifiedByName = "weeklyLimit")
    @Mapping(
            target = "totalCaloriesConsumed",
            source = "weeklyIntakeReport",
            qualifiedByName = "totalConsumed")
    @Mapping(
            target = "totalDeficit",
            source = "weeklyIntakeReport",
            qualifiedByName = "totalDeficit")
    @Mapping(
            target = "totalSurplus",
            source = "weeklyIntakeReport",
            qualifiedByName = "totalSurplus")
    @Mapping(
            target = "weeklyLimitReached",
            source = "weeklyIntakeReport",
            qualifiedByName = "limitReached")
    @Mapping(
            target = "weeklyLimitExceeded",
            source = "weeklyIntakeReport",
            qualifiedByName = "limitExceeded")
    @Mapping(
            target = "currentCalorieLimit",
            source = "weeklyIntakeReport",
            qualifiedByName = "currentLimit")
    @Mapping(
            target = "currentSurplus",
            source = "weeklyIntakeReport",
            qualifiedByName = "currentSurplus")
    @Mapping(
            target = "currentDeficit",
            source = "weeklyIntakeReport",
            qualifiedByName = "currentDeficit")
    @Mapping(
            target = "currentLimitReached",
            source = "weeklyIntakeReport",
            qualifiedByName = "currentLimitReached")
    @Mapping(
            target = "currentLimitExceeded",
            source = "weeklyIntakeReport",
            qualifiedByName = "currentLimitExceeded")
    public abstract WeeklyIntakeReportDTO toDTO(WeeklyIntakeReport weeklyIntakeReport);

    @Named("username")
    protected String getUsername(WeeklyIntakeReport weeklyIntakeReport) {
        return userService.findById(weeklyIntakeReport.getUserId()).getUsername();
    }

    @Named("totalDeficit")
    protected int getDeficit(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getWeeklyLimit(weeklyIntakeReport);
        int consumed = weeklyIntakeReport.sumTotalCalories();
        return ReportUtils.calculateDeficit(limit, consumed);
    }

    @Named("totalSurplus")
    protected int getSurplus(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getWeeklyLimit(weeklyIntakeReport);
        int consumed = weeklyIntakeReport.sumTotalCalories();
        return ReportUtils.calculateSurplus(limit, consumed);
    }

    @Named("currentDeficit")
    protected int getCurrentDeficit(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getCurrentCalorieLimit(weeklyIntakeReport);
        int consumed = weeklyIntakeReport.sumTotalCalories();
        return ReportUtils.calculateDeficit(limit, consumed);
    }

    @Named("currentSurplus")
    protected int getCurrentSurplus(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getCurrentCalorieLimit(weeklyIntakeReport);
        int consumed = weeklyIntakeReport.sumTotalCalories();
        return ReportUtils.calculateSurplus(limit, consumed);
    }

    @Named("limitReached")
    protected boolean getLimitReached(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getWeeklyLimit(weeklyIntakeReport);
        return ReportUtils.isLimitReached(limit, weeklyIntakeReport.sumTotalCalories());
    }

    @Named("limitExceeded")
    protected boolean getLimitExceeded(WeeklyIntakeReport weeklyIntakeReport) {
        int dailyLimit = getWeeklyLimit(weeklyIntakeReport);
        return ReportUtils.isLimitExceeded(dailyLimit, weeklyIntakeReport.sumTotalCalories());
    }

    @Named("currentLimitReached")
    protected boolean getCurrentLimitReached(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getCurrentCalorieLimit(weeklyIntakeReport);
        return ReportUtils.isLimitReached(limit, weeklyIntakeReport.sumTotalCalories());
    }

    @Named("currentLimitExceeded")
    protected boolean getCurrentLimitExceeded(WeeklyIntakeReport weeklyIntakeReport) {
        int dailyLimit = getCurrentCalorieLimit(weeklyIntakeReport);
        return ReportUtils.isLimitExceeded(dailyLimit, weeklyIntakeReport.sumTotalCalories());
    }

    @Named("weeklyLimit")
    protected int getWeeklyLimit(WeeklyIntakeReport weeklyIntakeReport) {
        Map<String, SingleDaySummary> weekdays = weeklyIntakeReport.getWeekdays();
        SingleDaySummary todaysSummary =
                new ArrayList<>(weekdays.values()).get(weekdays.size() - 1);
        int missingDays = 7 - DateUtils.getDayOfTheWeekNumber(todaysSummary.getDate());
        int userLimit = userService.findById(weeklyIntakeReport.getUserId()).getDailyLimit();
        int limit = 0;

        for (SingleDaySummary singleDaySummary : weekdays.values()) {
            limit += singleDaySummary.getCalorieLimit();
        }
        return limit + missingDays * userLimit;
    }

    @Named("currentLimit")
    protected int getCurrentCalorieLimit(WeeklyIntakeReport weeklyIntakeReport) {
        Map<String, SingleDaySummary> weekdays = weeklyIntakeReport.getWeekdays();
        final int[] sum = new int[1];
        weekdays.values().forEach(summary -> sum[0] += summary.getCalorieLimit());
        return sum[0];
    }

    @Named("totalConsumed")
    protected int getTotalConsumed(WeeklyIntakeReport weeklyIntakeReport) {
        return weeklyIntakeReport.sumTotalCalories();
    }
}
