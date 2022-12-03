package com.jruchel.caloriecounter.task;

import com.jruchel.caloriecounter.error.exceptions.UserNotFoundException;
import com.jruchel.caloriecounter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyUserReportScheduler {

    private final UserService userService;
    private final DailyUserReportJob dailyUserReportJob;

    @Scheduled(cron = "0 59 23 * * *")
    public void saveDailyUserReports() {
        userService
                .getAllUsers()
                .forEach(
                        user -> {
                            try {
                                dailyUserReportJob.saveUserReport(user);
                            } catch (UserNotFoundException e) {
                                log.error("Save daily user reports job failed");
                                log.error(e.getMessage());
                            }
                        });
    }
}
