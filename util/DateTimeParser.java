package util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
// import java.util.Arrays;
// import java.util.HashSet;

public class DateTimeParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    // private static final Integer[] monthsWithout31stArray = {4, 6, 9, 11};
    // private static final HashSet<Integer> monthsWithout31st = new HashSet<Integer>(Arrays.asList(monthsWithout31stArray));

    public static boolean isParseableAsDate(String date) {
        try {
            parseToDate(date);
        } catch (IllegalArgumentException iae) {
            return false;
        }
        return true;
    }

    public static boolean isParseableAsTime(String time) {
        try {
            parseToTime(time);
        } catch (IllegalArgumentException iae) {
            return false;
        }
        return true;
    }

    /**
     * Ensures that the specified date is valid.
     * <p>
     * A date is invalid if:
     * <ul>
     * <li>its month value is one of {4, 6, 9, 11} and its day value is 31 or more.</li>
     * <li>its month value is 2, and its day is 30 or more.</li>
     * <li>its year value is a non-leap year, its month value is 2, and its day value is 29.</li>
     * </ul>
     * and valid otherwise.
     * 
     * @return the specified date if it is valid.
     * @throws IllegalArgumentException if the specified date is invalid.
     */
    public static LocalDate verifyDateIsValid(LocalDate date) throws IllegalArgumentException {
        // not working, throw
        throw new UnsupportedOperationException("DateTimeParser.verifyDateIsValid is not working.");

        /*
        // handles no 31st month cases
        if ((monthsWithout31st.contains(date.getMonthValue()) && date.getDayOfMonth() >= 31)) {
            throw new IllegalArgumentException(String.format(String.join(" ",
                                                                         "Date is invalid: Illegal value for",
                                                                         "day when month is %d: %d"),
                                                             date.getMonthValue(), date.getDayOfMonth()));
        }
        // handles February cases
        if ((date.getMonthValue() == 2) &&
            (date.getDayOfMonth() >= 30) || (date.getYear() % 4 != 0 && date.getDayOfMonth() == 29)) {
            throw new IllegalArgumentException(String.format(String.join(" ",
                                                                         "Date is invalid: Invalid value for",
                                                                         "day when month is %d",
                                                                         "and year is %d: %d"),
                                                             date.getMonthValue(), date.getYear(),
                                                             date.getDayOfMonth()));
        }
        return date;
        */
    }

    /**
     * @throws IllegalArgumentException if the date cannot be parsed.
     * @see DateTimeParser.verifyDateisValid
     */
    public static LocalDate parseToDate(String date) throws IllegalArgumentException {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalArgumentException(String.format("String %s cannot be parsed as date.", date));
        }
    }

    public static LocalTime parseToTime(String time) throws IllegalArgumentException {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalArgumentException(String.format("String %s cannot be parsed as time.", time));
        }
    }
    
    public static String toString(LocalDate date) throws DateTimeException {
        return date.format(DATE_FORMATTER);
    }

    public static String toString(LocalTime time) throws DateTimeException {
        return time.format(TIME_FORMATTER);
    }
}
