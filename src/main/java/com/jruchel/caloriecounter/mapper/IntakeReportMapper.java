package com.jruchel.caloriecounter.mapper;

import com.jruchel.caloriecounter.model.api.report.DailyIntakeReportDTO;
import com.jruchel.caloriecounter.model.internal.DailyIntakeReport;
import org.mapstruct.Mapper;

@Mapper
public interface IntakeReportMapper {

    DailyIntakeReportDTO toDailyReportDTO(DailyIntakeReport dailyIntakeReport);
}
