package com.jruchel.caloriecounter.repository;

import com.jruchel.caloriecounter.model.internal.DailyIntakeReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyIntakeReportRepository extends MongoRepository<DailyIntakeReport, String> {}
