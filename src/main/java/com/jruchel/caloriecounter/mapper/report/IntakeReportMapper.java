package com.jruchel.caloriecounter.mapper.report;

import com.jruchel.caloriecounter.mapper.MealMapper;
import com.jruchel.caloriecounter.model.api.report.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.SingleDaySummaryDTO;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.model.internal.report.DailyIntakeReport;
import com.jruchel.caloriecounter.model.internal.report.SingleDaySummary;
import com.jruchel.caloriecounter.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = MealMapper.class)
public abstract class IntakeReportMapper {

    @Autowired private UserService userService;

    @Mapping(
            target = "leftToConsume",
            source = "dailyIntakeReport",
            qualifiedByName = "leftToConsume")
    @Mapping(target = "surplus", source = "dailyIntakeReport", qualifiedByName = "surplus")
    @Mapping(target = "username", source = "dailyIntakeReport", qualifiedByName = "username")
    @Mapping(
            target = "dailyLimitReached",
            source = "dailyIntakeReport",
            qualifiedByName = "limitReached")
    @Mapping(
            target = "dailyLimitExceeded",
            source = "dailyIntakeReport",
            qualifiedByName = "limitExceeded")
    public abstract DailyIntakeReportDTO toDailyReportDTO(DailyIntakeReport dailyIntakeReport);

    @Mapping(
            target = "dailyLimitReached",
            source = "dailyIntakeReport",
            qualifiedByName = "limitReached")
    @Mapping(
            target = "dailyLimitExceeded",
            source = "dailyIntakeReport",
            qualifiedByName = "limitExceeded")
    @Mapping(target = "surplusCalories", source = "dailyIntakeReport", qualifiedByName = "surplus")
    @Mapping(target = "caloricDeficit", source = "dailyIntakeReport", qualifiedByName = "deficit")
    public abstract SingleDaySummary toSingleDaySummary(DailyIntakeReport dailyIntakeReport);

    public abstract SingleDaySummaryDTO singleSummaryToDTO(SingleDaySummary singleDaySummary);

    @Named("leftToConsume")
    protected int getLeftToConsume(DailyIntakeReport dailyIntakeReport) {
        int limit = dailyIntakeReport.getCalorieLimit();
        int consumed = dailyIntakeReport.getCaloriesConsumed();
        if (consumed > limit) return 0;
        return limit - consumed;
    }

    @Named("surplus")
    protected int getSurplus(DailyIntakeReport dailyIntakeReport) {
        int limit = dailyIntakeReport.getCalorieLimit();
        int consumed = dailyIntakeReport.getCaloriesConsumed();
        if (consumed < limit) return 0;
        return consumed - limit;
    }

    @Named("deficit")
    protected int getDeficit(DailyIntakeReport dailyIntakeReport) {
        int limit = dailyIntakeReport.getCalorieLimit();
        int consumed = dailyIntakeReport.getCaloriesConsumed();
        if (consumed > limit) return 0;
        return limit - consumed;
    }

    @Named("username")
    protected String getUsername(DailyIntakeReport dailyIntakeReport) {
        User user = userService.findById(dailyIntakeReport.getUserId());
        return user.getUsername();
    }

    @Named("limitReached")
    protected boolean isDailyLimitReached(DailyIntakeReport dailyIntakeReport) {
        int dailyLimit = dailyIntakeReport.getCalorieLimit();
        int difference = dailyLimit - dailyIntakeReport.sumDailyCalories();
        return difference < (double) 2 / 100 * dailyLimit;
    }

    @Named("limitExceeded")
    protected boolean isDailyLimitExceeded(DailyIntakeReport dailyIntakeReport) {
        if (!isDailyLimitReached(dailyIntakeReport)) return false;
        int dailyLimit = dailyIntakeReport.getCalorieLimit();
        int difference = Math.abs(dailyLimit - dailyIntakeReport.sumDailyCalories());
        return difference > (double) 2 / 100 * dailyLimit;
    }
}
