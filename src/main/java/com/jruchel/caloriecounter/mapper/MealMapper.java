package com.jruchel.caloriecounter.mapper;

import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import com.jruchel.caloriecounter.model.internal.Meal;
import org.mapstruct.Mapper;

@Mapper
public interface MealMapper {

    MealDTO toDTO(Meal meal);
}
