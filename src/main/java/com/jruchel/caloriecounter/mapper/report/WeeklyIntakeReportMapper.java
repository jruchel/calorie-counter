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
    @Mapping(target = "totalDeficit", source = "weeklyIntakeReport", qualifiedByName = "deficit")
    @Mapping(target = "totalSurplus", source = "weeklyIntakeReport", qualifiedByName = "surplus")
    @Mapping(
            target = "weeklyLimitReached",
            source = "weeklyIntakeReport",
            qualifiedByName = "limitReached")
    @Mapping(
            target = "weeklyLimitExceeded",
            source = "weeklyIntakeReport",
            qualifiedByName = "limitExceeded")
    public abstract WeeklyIntakeReportDTO toDTO(WeeklyIntakeReport weeklyIntakeReport);

    @Named("username")
    protected String getUsername(WeeklyIntakeReport weeklyIntakeReport) {
        return userService.findById(weeklyIntakeReport.getUserId()).getUsername();
    }

    @Named("deficit")
    protected int getDeficit(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getWeeklyLimit(weeklyIntakeReport);
        int consumed = weeklyIntakeReport.sumTotalCalories();
        return ReportUtils.calculateDeficit(limit, consumed);
    }

    @Named("surplus")
    protected int getSurplus(WeeklyIntakeReport weeklyIntakeReport) {
        int limit = getWeeklyLimit(weeklyIntakeReport);
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

    @Named("totalConsumed")
    protected int getTotalConsumed(WeeklyIntakeReport weeklyIntakeReport) {
        return weeklyIntakeReport.sumTotalCalories();
    }
}
