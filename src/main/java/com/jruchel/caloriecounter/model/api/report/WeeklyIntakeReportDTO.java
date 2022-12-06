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
    private int currentCalorieLimit;
    private int currentDeficit;
    private int currentSurplus;
    private boolean currentLimitReached;
    private boolean currentLimitExceeded;
    private int weeklyCalorieLimit;
    private int totalDeficit;
    private int totalSurplus;
    private boolean weeklyLimitReached;
    private boolean weeklyLimitExceeded;
}
