package com.seol.communityfeed.common.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeCalculator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TimeCalculator(){}

    public static LocalDate getDateDaysAgo(int daysAgo){
        LocalDate currDate = LocalDate.now();
        return  currDate.minusDays(daysAgo);
    }

    public static String getFormattedDate(LocalDateTime dateTime){
        if (dateTime == null){
            return "";
        }
        return dateTime.format(FORMATTER);
    }
}
