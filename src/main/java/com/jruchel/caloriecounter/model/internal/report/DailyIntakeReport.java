package com.jruchel.caloriecounter.model.internal.report;

import com.jruchel.caloriecounter.model.internal.Meal;
import java.util.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyIntakeReport {

    @Id private String id;
    private String userId;
    private Date date;
    private int calorieLimit;
    @Builder.Default private List<Meal> meals = new ArrayList<>();

    public int sumDailyCalories() {
        return sumDailyCalories(meals);
    }

    public static int sumDailyCalories(List<Meal> meals) {
        int sum = 0;
        for (Meal m : meals) {
            sum += m.getCaloriesSum();
        }
        return sum;
    }
}
