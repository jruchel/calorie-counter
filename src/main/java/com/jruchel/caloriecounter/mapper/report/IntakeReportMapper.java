package com.jruchel.caloriecounter.mapper.report;

import com.jruchel.caloriecounter.mapper.MealMapper;
import com.jruchel.caloriecounter.model.api.report.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.SingleDaySummaryDTO;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.model.internal.report.DailyIntakeReport;
import com.jruchel.caloriecounter.model.internal.report.SingleDaySummary;
import com.jruchel.caloriecounter.service.UserService;
import com.jruchel.caloriecounter.utils.ReportUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = MealMapper.class)
public abstract class IntakeReportMapper {

    @Autowired private UserService userService;

    @Mapping(
            target = "caloriesConsumed",
            source = "dailyIntakeReport",
            qualifiedByName = "consumed")
    @Mapping(target = "leftToConsume", source = "dailyIntakeReport", qualifiedByName = "deficit")
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
            target = "caloriesConsumed",
            source = "dailyIntakeReport",
            qualifiedByName = "consumed")
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

    @Named("consumed")
    protected int getConsumed(DailyIntakeReport dailyIntakeReport) {
        return dailyIntakeReport.sumDailyCalories();
    }

    @Named("surplus")
    protected int getSurplus(DailyIntakeReport dailyIntakeReport) {
        int limit = dailyIntakeReport.getCalorieLimit();
        int consumed = dailyIntakeReport.sumDailyCalories();
        return ReportUtils.calculateSurplus(limit, consumed);
    }

    @Named("deficit")
    protected int getDeficit(DailyIntakeReport dailyIntakeReport) {
        int limit = dailyIntakeReport.getCalorieLimit();
        int consumed = dailyIntakeReport.sumDailyCalories();
        return ReportUtils.calculateDeficit(limit, consumed);
    }

    @Named("username")
    protected String getUsername(DailyIntakeReport dailyIntakeReport) {
        User user = userService.findById(dailyIntakeReport.getUserId());
        return user.getUsername();
    }

    @Named("limitReached")
    protected boolean isDailyLimitReached(DailyIntakeReport dailyIntakeReport) {
        int dailyLimit = dailyIntakeReport.getCalorieLimit();
        return ReportUtils.isLimitReached(dailyLimit, dailyIntakeReport.sumDailyCalories());
    }

    @Named("limitExceeded")
    protected boolean isDailyLimitExceeded(DailyIntakeReport dailyIntakeReport) {
        int dailyLimit = dailyIntakeReport.getCalorieLimit();
        return ReportUtils.isLimitExceeded(dailyLimit, dailyIntakeReport.sumDailyCalories());
    }
}
