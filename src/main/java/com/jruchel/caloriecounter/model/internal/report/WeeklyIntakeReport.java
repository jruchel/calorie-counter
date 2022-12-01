package com.jruchel.caloriecounter.model.internal.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklyIntakeReport {

    @Id private String id;
    private String userId;
    @Builder.Default private Map<String, SingleDaySummary> weekdays = new HashMap<>();

    public int sumTotalCalories() {
        return sumTotalCalories(new ArrayList<>(weekdays.values()));
    }

    public static int sumTotalCalories(List<SingleDaySummary> singleDaySummaries) {
        int sum = 0;

        for (SingleDaySummary s : singleDaySummaries) {
            sum += s.getCaloriesConsumed();
        }
        return sum;
    }
}
