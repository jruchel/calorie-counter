package com.jruchel.caloriecounter.controller.intake;

import com.jruchel.caloriecounter.error.exceptions.UserNotFoundException;
import com.jruchel.caloriecounter.mapper.report.IntakeReportMapper;
import com.jruchel.caloriecounter.mapper.report.WeeklyIntakeReportMapper;
import com.jruchel.caloriecounter.model.api.report.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.MonthlyIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.PeriodIntakeReportDTO;
import com.jruchel.caloriecounter.model.api.report.WeeklyIntakeReportDTO;
import com.jruchel.caloriecounter.service.IntakeReportService;
import com.jruchel.caloriecounter.utils.DateUtils;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/intake/report")
@RequiredArgsConstructor
public class IntakeReportController {

    private final IntakeReportMapper intakeReportMapper;
    private final IntakeReportService intakeReportService;
    private final WeeklyIntakeReportMapper weeklyIntakeReportMapper;

    @GetMapping("/day/{username}")
    public ResponseEntity<DailyIntakeReportDTO> getDailyReport(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date date)
            throws UserNotFoundException {
        DailyIntakeReportDTO responseBody;
        if (date == null || DateUtils.isSameDay(date, new Date())) {
            responseBody =
                    intakeReportMapper.toDailyReportDTO(
                            intakeReportService.generateDailyIntakeReport(username));
        } else {
            responseBody =
                    intakeReportMapper.toDailyReportDTO(
                            intakeReportService.getDailyIntakeReport(username, date));
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/day/today/{username}")
    public ResponseEntity<DailyIntakeReportDTO> getTodaysReport(
            @PathVariable(required = false) String username) throws UserNotFoundException {
        DailyIntakeReportDTO responseBody =
                intakeReportMapper.toDailyReportDTO(
                        intakeReportService.generateDailyIntakeReport(username));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/day/yesterday/{username}")
    public ResponseEntity<DailyIntakeReportDTO> getYesterdaysReport(
            @PathVariable(required = false) String username) throws UserNotFoundException {
        DailyIntakeReportDTO responseBody =
                intakeReportMapper.toDailyReportDTO(
                        intakeReportService.getDailyIntakeReport(
                                username, DateUtils.getPreviousDay(new Date())));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/week/current/{username}")
    public ResponseEntity<WeeklyIntakeReportDTO> getWeeklyReport(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date date)
            throws UserNotFoundException {
        WeeklyIntakeReportDTO responseBody;
        if (date == null) date = new Date();
        responseBody =
                weeklyIntakeReportMapper.toDTO(
                        intakeReportService.generateWeeklyIntakeReport(username, date));

        return ResponseEntity.ok(responseBody);
    }

    // TODO implement
    @GetMapping("/month/current/{username}")
    public ResponseEntity<MonthlyIntakeReportDTO> getMonthReport(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date date) {
        throw new UnsupportedOperationException();
    }

    // TODO implement
    @GetMapping("/period/{username}")
    public ResponseEntity<PeriodIntakeReportDTO> getPeriodReport(@PathVariable String username) {
        throw new UnsupportedOperationException();
    }
}
