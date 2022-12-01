package com.jruchel.caloriecounter.model.internal.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jruchel.caloriecounter.model.internal.Meal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleDaySummary {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    private int calorieLimit;
    private int caloriesConsumed;
    private int surplusCalories;
    private int caloricDeficit;
    @Builder.Default private List<Meal> meals = new ArrayList<>();
    @Builder.Default private boolean dailyLimitReached = false;
    @Builder.Default private boolean dailyLimitExceeded = false;
}
