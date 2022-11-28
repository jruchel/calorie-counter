package com.jruchel.caloriecounter.model.internal;

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
    private String username;
    private Date date;
    private int calorieLimit;
    private int caloriesConsumed;
    @Builder.Default private List<Meal> meals = new ArrayList<>();
    private int leftToConsume;
    @Builder.Default private boolean dailyLimitReached = false;
    @Builder.Default private boolean dailyLimitExceeded = false;
}
