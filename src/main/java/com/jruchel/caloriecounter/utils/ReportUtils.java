package com.jruchel.caloriecounter.utils;

public class ReportUtils {

    public static int calculateDeficit(int calorieLimit, int caloriesConsumed) {
        if (caloriesConsumed > calorieLimit) return 0;
        return calorieLimit - caloriesConsumed;
    }

    public static int calculateSurplus(int calorieLimit, int caloriesConsumed) {
        if (caloriesConsumed < calorieLimit) return 0;
        return caloriesConsumed - calorieLimit;
    }

    public static boolean isLimitReached(int limit, int caloriesConsumed) {
        int difference = limit - caloriesConsumed;
        return difference < (double) 2 / 100 * limit;
    }

    public static boolean isLimitExceeded(int limit, int caloriesConsumed) {
        if (!isLimitReached(limit, caloriesConsumed)) return false;
        int difference = caloriesConsumed - limit;
        return difference > (double) 2 / 100 * limit;
    }
}
