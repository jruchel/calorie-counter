package com.jruchel.caloriecounter.service;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static boolean isSameDay(Date date, Date date2) {
        return removeTime(date).equals(removeTime(date2));
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
