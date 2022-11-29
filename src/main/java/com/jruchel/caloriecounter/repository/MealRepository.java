package com.jruchel.caloriecounter.repository;

import com.jruchel.caloriecounter.model.internal.Meal;
import com.jruchel.caloriecounter.service.DateUtils;
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

    default List<Meal> findTodaysMealsByUser(final String userId) {
        return findMealsByUser(userId).stream()
                .filter(meal -> DateUtils.isSameDay(meal.getTime(), new Date()))
                .collect(Collectors.toList());
    }

    default List<Meal> findMealsByDayForUser(String userId, Date date) {
        return findMealsByUser(userId).stream()
                .filter(
                        meal ->
                                DateUtils.removeTime(meal.getTime())
                                        .equals(DateUtils.removeTime(date)))
                .collect(Collectors.toList());
    }

    default Meal findMealByDayAndNameForUser(String userId, Date date, String name) {
        return findMealsByDayForUser(userId, date).stream()
                .filter(meal -> meal.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
