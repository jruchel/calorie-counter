package com.jruchel.caloriecounter.controller;

import com.jruchel.caloriecounter.mapper.MealMapper;
import com.jruchel.caloriecounter.model.api.meal.MealAdditionRequest;
import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import com.jruchel.caloriecounter.model.api.meal.MealDeletionRequest;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.service.MealService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intake")
@RequiredArgsConstructor
public class IntakeController {

    private final MealService mealService;
    private final MealMapper mealMapper;

    @PostMapping("/meals/{username}")
    public ResponseEntity<MealDTO> addMeal(
            @RequestBody @Valid MealAdditionRequest mealAdditionRequest,
            @PathVariable String username) {
        Meal meal =
                mealService.addMeal(
                        username,
                        mealAdditionRequest.getName(),
                        mealAdditionRequest.getCalories(),
                        mealAdditionRequest.getFoods());

        return ResponseEntity.status(201).body(mealMapper.toDTO(meal));
    }

    @DeleteMapping("/meals/{username}")
    public ResponseEntity<MealDTO> deleteMeal(
            @RequestBody MealDeletionRequest mealDeletionRequest, @PathVariable String username) {
        MealDTO responseBody =
                mealMapper.toDTO(
                        mealService.deleteMeal(
                                username,
                                mealDeletionRequest.getName(),
                                mealDeletionRequest.getDate()));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/meals/{username}")
    public ResponseEntity<List<MealDTO>> getMealsByUser(@PathVariable String username) {
        List<MealDTO> responseBody = mealMapper.toDTOList(mealService.getMealsByUser(username));

        return ResponseEntity.ok(responseBody);
    }
}
