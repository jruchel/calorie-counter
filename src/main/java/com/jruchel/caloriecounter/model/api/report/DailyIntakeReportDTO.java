package com.jruchel.caloriecounter.model.api.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jruchel.caloriecounter.model.api.meal.MealDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyIntakeReportDTO {

    private String username;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    private int calorieLimit;
    private int caloriesConsumed;
    @Builder.Default private List<MealDTO> meals = new ArrayList<>();
    private int leftToConsume;
    @Builder.Default private boolean dailyLimitReached = false;
    @Builder.Default private boolean dailyLimitExceeded = false;
}
