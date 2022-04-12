package util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static boolean isParseableAsDate(String date) {
        try {
            parseToDate(date);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    public static boolean isParseableAsTime(String time) {
        try {
            parseToTime(time);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    public static LocalDate parseToDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static LocalTime parseToTime(String time) throws DateTimeParseException {
        return LocalTime.parse(time, TIME_FORMATTER);
    }
    
    public static String convertToString(LocalDate date) throws DateTimeException {
        return date.format(DATE_FORMATTER);
    }

    public static String convertToString(LocalTime time) throws DateTimeException {
        return time.format(TIME_FORMATTER);
    }
}
