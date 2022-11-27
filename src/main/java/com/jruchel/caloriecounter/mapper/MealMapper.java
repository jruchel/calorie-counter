package com.jruchel.caloriecounter.mapper;

import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import com.jruchel.caloriecounter.model.internal.Meal;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface MealMapper {

    MealDTO toDTO(Meal meal);

    List<MealDTO> toDTOList(List<Meal> meals);
}
