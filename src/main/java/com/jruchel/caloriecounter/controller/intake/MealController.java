package com.jruchel.caloriecounter.controller.intake;

import com.jruchel.caloriecounter.error.NutritionInformationNotFound;
import com.jruchel.caloriecounter.error.UserNotFoundException;
import com.jruchel.caloriecounter.mapper.MealMapper;
import com.jruchel.caloriecounter.model.api.meal.MealAdditionRequest;
import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.service.MealService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intake/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final MealMapper mealMapper;

    @PostMapping("/{username}")
    public ResponseEntity<MealDTO> addMeal(
            @RequestBody @Valid MealAdditionRequest mealAdditionRequest,
            @PathVariable String username)
            throws UserNotFoundException, NutritionInformationNotFound {
        Meal meal =
                mealService.addMeal(
                        username, mealAdditionRequest.getName(), mealAdditionRequest.getFoods());

        return ResponseEntity.status(201).body(mealMapper.toDTO(meal));
    }

    @DeleteMapping("/{username}/{mealName}")
    public ResponseEntity<MealDTO> deleteMeal(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "mealName") String mealName,
            @RequestParam(required = false) Date date)
            throws UserNotFoundException {
        if (date == null) date = new Date();
        MealDTO responseBody = mealMapper.toDTO(mealService.deleteMeal(username, mealName, date));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{username}/today")
    public ResponseEntity<List<MealDTO>> getTodaysMealsByUser(@PathVariable String username)
            throws UserNotFoundException {
        List<MealDTO> responseBody =
                mealMapper.toDTOList(mealService.getTodaysMealsForUser(username));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<MealDTO>> getMealsByUser(@PathVariable String username)
            throws UserNotFoundException {
        List<MealDTO> responseBody = mealMapper.toDTOList(mealService.getMealsByUser(username));
        return ResponseEntity.ok(responseBody);
    }
}
