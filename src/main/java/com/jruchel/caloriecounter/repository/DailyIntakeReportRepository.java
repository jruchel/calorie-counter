package com.jruchel.caloriecounter.repository;

import com.jruchel.caloriecounter.model.internal.report.DailyIntakeReport;
import com.jruchel.caloriecounter.service.DateUtils;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyIntakeReportRepository extends MongoRepository<DailyIntakeReport, String> {

    List<DailyIntakeReport> findDailyReportsByUserId(String userId);

    default DailyIntakeReport findDailyReportByUserAndDay(String userId, Date date) {
        return findDailyReportsByUserId(userId).stream()
                .filter(r -> DateUtils.isSameDay(r.getDate(), date))
                .findFirst()
                .orElse(null);
    }
}
