package com.jruchel.caloriecounter.controller;

import com.jruchel.caloriecounter.model.api.meal.MealAdditionRequest;
import com.jruchel.caloriecounter.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intake")
@RequiredArgsConstructor
public class IntakeController {

    private final MealService mealService;

    @PostMapping
    public ResponseEntity<Void> addMeal(@RequestBody MealAdditionRequest mealAdditionRequest) {
        mealService.addMeal(
                mealAdditionRequest.getUsername(),
                mealAdditionRequest.getName(),
                mealAdditionRequest.getCalories());

        return ResponseEntity.status(201).build();
    }
}
