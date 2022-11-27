package com.jruchel.caloriecounter.service;

import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.repository.MealRepository;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService extends AbstractService<Meal> {

    private final MealRepository mealRepository;
    private final UserService userService;

    public Meal addMeal(final String username, final String name, final int calories) {
        User user = userService.findByUsername(username);
        Meal entry =
                new Meal(UUID.randomUUID().toString(), user.getId(), name, calories, new Date());
        return mealRepository.insert(entry);
    }
}
