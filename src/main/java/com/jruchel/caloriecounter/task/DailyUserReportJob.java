package com.jruchel.caloriecounter.task;

import com.jruchel.caloriecounter.model.internal.User;
import com.jruchel.caloriecounter.model.internal.report.DailyIntakeReport;
import com.jruchel.caloriecounter.service.IntakeReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyUserReportJob {

    private final IntakeReportService intakeReportService;

    @Async
    public void saveUserReport(User user) {
        DailyIntakeReport dailyIntakeReport =
                intakeReportService.generateDailyIntakeReport(user.getUsername());
        intakeReportService.saveDailyIntakeReport(dailyIntakeReport);
    }
}
