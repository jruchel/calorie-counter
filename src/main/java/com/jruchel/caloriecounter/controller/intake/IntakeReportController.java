package com.jruchel.caloriecounter.controller.intake;

import com.jruchel.caloriecounter.mapper.IntakeReportMapper;
import com.jruchel.caloriecounter.model.api.report.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.MonthlyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.PeriodIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.WeeklyIntakeReportDTO;
import com.jruchel.caloriecounter.service.DateUtils;
import com.jruchel.caloriecounter.service.IntakeReportService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intake/report")
@RequiredArgsConstructor
public class IntakeReportController {

    private final IntakeReportMapper intakeReportMapper;
    private final IntakeReportService intakeReportService;

    @GetMapping("/day/{username}")
    public ResponseEntity<DailyIntakeReportDTO> getDailyReport(
            @PathVariable String username, @RequestParam Date date) {
        if (date == null) date = new Date();
        DailyIntakeReportDTO responseBody =
                intakeReportMapper.toDailyReportDTO(
                        intakeReportService.generateDailyIntakeReport(username, date));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/day/yesterday/{username}")
    public ResponseEntity<DailyIntakeReportDTO> getYesterdaysReport(@PathVariable String username) {
        DailyIntakeReportDTO responseBody =
                intakeReportMapper.toDailyReportDTO(
                        intakeReportService.generateDailyIntakeReport(
                                username, DateUtils.getPreviousDay(new Date())));
        return ResponseEntity.ok(responseBody);
    }

    // TODO implement
    @GetMapping("/week/current/{username}")
    public ResponseEntity<WeeklyIntakeReportDTO> getWeeklyReport(
            @PathVariable String username, @RequestParam Date date) {
        throw new UnsupportedOperationException();
    }

    // TODO implement
    @GetMapping("/month/current/{username}")
    public ResponseEntity<MonthlyIntakeReportDTO> getMonthReport(
            @PathVariable String username, @RequestParam Date date) {
        throw new UnsupportedOperationException();
    }

    // TODO implement
    @GetMapping("/period/{username}")
    public ResponseEntity<PeriodIntakeReportDTO> getPeriodReport(@PathVariable String username) {
        throw new UnsupportedOperationException();
    }
}
