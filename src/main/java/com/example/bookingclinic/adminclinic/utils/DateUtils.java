package com.example.bookingclinic.adminclinic.utils;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static LocalDate getStartOfWeek(LocalDate date){
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static LocalDate getEndOfWeek(LocalDate date){
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public static String getVietNameseDayOfWeek(LocalDate date){
        DayOfWeek day = date.getDayOfWeek();
        return switch (day) {
            case MONDAY -> "Thứ 2";
            case TUESDAY -> "Thứ 3";
            case WEDNESDAY -> "Thứ 4";
            case THURSDAY -> "Thứ 5";
            case FRIDAY -> "Thứ 6";
            case SATURDAY -> "Thứ 7";
            case SUNDAY -> "CN";
        };
    }
}
