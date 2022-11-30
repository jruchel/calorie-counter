package com.jruchel.caloriecounter.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtils {

    public static boolean isSameDay(Date date, Date date2) {
        return removeTime(date).equals(removeTime(date2));
    }

    public static Date getPreviousDay(Date date) {
        return Date.from(
                convertToLocalDateViaInstant(date)
                        .minusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static int getWeekOfTheMonthNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public static String getMonthName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }

    public static List<Date> getWeekDays(Date date) {
        List<Date> result = new ArrayList<>();
        Date monday =
                Date.from(
                        LocalDateTime.now(ZoneId.systemDefault())
                                .with(LocalTime.MIN)
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                                .atZone(ZoneId.systemDefault())
                                .toInstant());
        result.add(monday);

        for (int i = 0; i < 8; i++) {
            result.add(getNextDay(result.get(i)));
        }

        return result;
    }

    public static Date getNextDay(Date date) {
        removeTime(date);
        LocalDateTime nextDay = convertToLocalDateViaInstant(date).plusDays(1);
        return Date.from(nextDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public static Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
