package com.jruchel.caloriecounter.repository;

import com.jruchel.caloriecounter.model.internal.Meal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {

    default List<Meal> findMealsByUser(String userId) {
        ExampleMatcher matcher =
                ExampleMatcher.matchingAny()
                        .withIgnoreCase()
                        .withMatcher(
                                "userId",
                                ExampleMatcher.GenericPropertyMatcher.of(
                                        ExampleMatcher.StringMatcher.EXACT));
        Example<Meal> mealExample = Example.of(Meal.builder().userId(userId).build(), matcher);
        return findAll(mealExample);
    }

    default List<Meal> findMealsByDateForUser(String userId, Date date) {
        return findMealsByUser(userId).stream()
                .filter(meal -> meal.getDate().equals(date))
                .collect(Collectors.toList());
    }

    default Meal findMealByDateAndNameForUser(String userId, Date date, String name) {
        return findMealsByDateForUser(userId, date).stream()
                .filter(meal -> meal.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
