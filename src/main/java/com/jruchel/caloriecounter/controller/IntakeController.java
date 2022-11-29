package com.jruchel.caloriecounter.controller;

import com.jruchel.caloriecounter.mapper.MealMapper;
import com.jruchel.caloriecounter.model.api.meal.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.meal.MealAdditionRequest;
import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.service.IntakeReportService;
import com.jruchel.caloriecounter.service.MealService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intake")
@RequiredArgsConstructor
public class IntakeController {

    private final MealService mealService;
    private final MealMapper mealMapper;
    private final IntakeReportService intakeReportService;

    @PostMapping("/meals/{username}")
    public ResponseEntity<MealDTO> addMeal(
            @RequestBody @Valid MealAdditionRequest mealAdditionRequest,
            @PathVariable String username) {
        Meal meal =
                mealService.addMeal(
                        username, mealAdditionRequest.getName(), mealAdditionRequest.getFoods());

        return ResponseEntity.status(201).body(mealMapper.toDTO(meal));
    }

    @GetMapping("/report/daily/{username}")
    public ResponseEntity<DailyIntakeReportDTO> getDailyIntakeReport(
            @PathVariable String username) {
        DailyIntakeReportDTO responseBody =
                mealMapper.toDailyReportDTO(
                        intakeReportService.generateDailyIntakeReport(username));
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/meals/{username}/{mealName}")
    public ResponseEntity<MealDTO> deleteMeal(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "mealName") String mealName,
            @RequestParam(required = false) Date date) {
        if (date == null) date = new Date();
        MealDTO responseBody = mealMapper.toDTO(mealService.deleteMeal(username, mealName, date));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/meals/{username}/today")
    public ResponseEntity<List<MealDTO>> getTodaysMealsByUser(@PathVariable String username) {
        List<MealDTO> responseBody =
                mealMapper.toDTOList(mealService.getTodaysMealsForUser(username));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/meals/{username}")
    public ResponseEntity<List<MealDTO>> getMealsByUser(@PathVariable String username) {
        List<MealDTO> responseBody = mealMapper.toDTOList(mealService.getMealsByUser(username));
        return ResponseEntity.ok(responseBody);
    }
}
