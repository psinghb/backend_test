package com.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Util {

    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public static Long toLong(String str) {
        return Long.parseLong(str);
    }

    public static int toInt(String str) {
        return Integer.parseInt(str);
    }

    public static LocalDate parseDate(String s) {
        return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
