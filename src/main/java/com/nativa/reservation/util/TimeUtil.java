package com.nativa.reservation.util;

import java.time.LocalTime;

public class TimeUtil {

    public static String formatHour(Integer hour){
        return (hour < 10 ) ? String.format("0%s", hour) : hour.toString();
    }
}
