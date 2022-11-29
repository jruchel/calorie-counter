package com.jruchel.caloriecounter.mapper;

import com.jruchel.caloriecounter.model.api.meal.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import com.jruchel.caloriecounter.model.internal.DailyIntakeReport;
import com.jruchel.caloriecounter.model.internal.Meal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MealMapper {

    @Mapping(target = "calories", source = "meal")
    MealDTO toDTO(Meal meal);

    List<MealDTO> toDTOList(List<Meal> meals);

    DailyIntakeReportDTO toDailyReportDTO(DailyIntakeReport dailyIntakeReport);

    default int sumMealCalories(Meal meal) {
        return meal.getCaloriesSum();
    }
}
