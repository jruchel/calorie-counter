package com.jruchel.caloriecounter.model.api.report;

import java.util.HashMap;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklyIntakeReportDTO {

    private String username;
    @Builder.Default private Map<String, SingleDaySummaryDTO> weekdays = new HashMap<>();
    private int totalCaloriesConsumed;
    private int totalDeficit;
    private int totalSurplus;
    private int weeklyCalorieLimit;
    private boolean weeklyLimitReached;
    private boolean weeklyLimitExceeded;
}
