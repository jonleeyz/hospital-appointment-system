package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class DateTimeParserUnitTest {
    // valid dates
    private static final String date1Raw = "18032018";
    private static final String date2Raw = "12122012";
    private static final String date3Raw = "111111";
    private static final String dateFeb29LeapYearRaw = "29022000";
    private static final LocalDate date1 = LocalDate.of(2018, 3, 18);
    private static final LocalDate date2 = LocalDate.of(2012, 12, 12);
    private static final LocalDate date3 = LocalDate.of(2011, 11, 11);
    private static final LocalDate dateFeb29LeapYear = LocalDate.of(2000, 2, 29);

    // valid times
    private static final String time1Raw = "09:00:00";
    private static final String time2Raw = "10:00:00";
    private static final String time3Raw = "09:09:09";
    private static final LocalTime time1 = LocalTime.of(9, 0, 0);
    private static final LocalTime time2 = LocalTime.of(10, 0, 0);
    private static final LocalTime time3 = LocalTime.of(9, 9, 9);

    // parseable invalid dates
    private static final String dateInvalidApr31Raw = "31042000";
    private static final String dateInvalidJun31Raw = "31062000";
    private static final String dateInvalidSep31Raw = "31092000";
    private static final String dateInvalidNov31Raw = "31112000";
    private static final String dateInvalidFeb30LeapYearRaw = "30022000";
    private static final String dateInvalidFeb31LeapYearRaw = "31022000";
    private static final String dateInvalidFeb29NonLeapYearRaw = "29022001";
    private static final String dateInvalidFeb30NonLeapYearRaw = "30022001";
    private static final String dateInvalidFeb31NonLeapYearRaw = "31022001";
    private static final LocalDate dateInvalidApr31 = LocalDate.of(2000, 4, 31);
    private static final LocalDate dateInvalidJun31 = LocalDate.of(2000, 6, 31);
    private static final LocalDate dateInvalidSep31 = LocalDate.of(2000, 9, 31);
    private static final LocalDate dateInvalidNov31 = LocalDate.of(2000, 11, 31);
    private static final LocalDate dateInvalidFeb30LeapYear = LocalDate.of(2000, 2, 30);
    private static final LocalDate dateInvalidFeb31LeapYear = LocalDate.of(2000, 2, 31);
    private static final LocalDate dateInvalidFeb29NonLeapYear = LocalDate.of(2001, 2, 20);
    private static final LocalDate dateInvalidFeb30NonLeapYear = LocalDate.of(2001, 2, 30);
    private static final LocalDate dateInvalidFeb31NonLeapYear = LocalDate.of(2001, 2, 31);

    // unparseable strings
    private static final String blankString = "";
    private static final String randomString = "randomString";
    private static final String dateInvalidFormat1 = "180418";
    private static final String dateInvalidFormat2 = "18/04/2018";
    private static final String dateInvalidFormat3 = "18-04-2018";
    private static final String dateInvalidFormat4 = "18.04.2018";
    private static final String dateInvalidFormat5 = "18 Apr 2018";
    private static final String dateInvalidFormat6 = "Apr 18 2018";
    private static final String timeInvalidFormat1 = "09.00.00";
    private static final String timeInvalidFormat2 = "09-00-00";
    private static final String timeInvalidFormat3 = "9:00:00";
    private static final String timeInvalidFormat4 = "09:00";
    private static final String timeInvalidFormat5 = "9:00";
    private static final String timeInvalidFormat6 = "9 PM";

    private static Stream<String> provideUnparseableDatesRaw() {
        return Stream.of(blankString, randomString, time1Raw,
                         dateInvalidFormat1, dateInvalidFormat2, dateInvalidFormat3,
                         dateInvalidFormat4, dateInvalidFormat5, dateInvalidFormat6);
    }

    private static Stream<String> provideUnparseableTimesRaw() {
        return Stream.of(blankString, randomString, date1Raw, timeInvalidFormat1, timeInvalidFormat2,
                         timeInvalidFormat3, timeInvalidFormat4, timeInvalidFormat5, timeInvalidFormat6);
    }

    private static Stream<LocalDate> verifyDateIsValidFailsCorrectlyForNonFebInvalidDates() {
        return Stream.of(dateInvalidApr31, dateInvalidJun31, dateInvalidSep31, dateInvalidNov31);
    }

    private static Stream<LocalDate> verifyDateIsValidWorksCorrectlyForValidDates() {
        return Stream.of(date1, date2, date3, dateFeb29LeapYear);
    }

    private static Stream<LocalDate> verifyDateIsValidFailsCorrectlyForFebInvalidDates() {
        return Stream.of(dateInvalidFeb30LeapYear, dateInvalidFeb31LeapYear,
                         dateInvalidFeb29NonLeapYear, dateInvalidFeb30NonLeapYear, dateInvalidFeb31NonLeapYear);
    }

    private static Stream<Arguments> parseToDateWorksCorrectlyWithValidParseableDates() {
        return Stream.of(Arguments.of(date1Raw, date1),
                         Arguments.of(date2Raw, date2),
                         Arguments.of(date3Raw, date3),
                         Arguments.of(dateFeb29LeapYearRaw, dateFeb29LeapYear));
    }

    private static Stream<Arguments> parseToDateFailsCorrectlyForNonFebInvalidParseableDates() {
        return Stream.of(Arguments.of(dateInvalidApr31Raw, dateInvalidApr31),
                         Arguments.of(dateInvalidJun31Raw, dateInvalidJun31),
                         Arguments.of(dateInvalidSep31Raw, dateInvalidSep31),
                         Arguments.of(dateInvalidNov31Raw, dateInvalidNov31));
    }

    private static Stream<Arguments> parseToDateFailsCorrectlyForFebInvalidParseableDates() {
        return Stream.of(Arguments.of(dateInvalidFeb30LeapYearRaw, dateInvalidFeb30LeapYear),
                         Arguments.of(dateInvalidFeb31LeapYearRaw, dateInvalidFeb31LeapYear),
                         Arguments.of(dateInvalidFeb29NonLeapYearRaw, dateInvalidFeb29NonLeapYear),
                         Arguments.of(dateInvalidFeb30NonLeapYearRaw, dateInvalidFeb30NonLeapYear),
                         Arguments.of(dateInvalidFeb31NonLeapYearRaw, dateInvalidFeb31LeapYear));
    }

    private static Stream<Arguments> parseToTimeWorksCorrectlyForParseableTimes() {
        return Stream.of(Arguments.of(time1Raw, time1),
                         Arguments.of(time2Raw, time2),
                         Arguments.of(time3Raw, time3));
    }

    private static Stream<Arguments> toStringForDateWorksCorrectly() {
        return Stream.of(Arguments.of(date1, date1Raw),
                         Arguments.of(date2, date2Raw),
                         Arguments.of(date3, date3Raw),
                         Arguments.of(dateFeb29LeapYear, dateFeb29LeapYearRaw));
    }

    private static Stream<Arguments> toStringForTimeWorksCorrectly() {
        return Stream.of(Arguments.of(time1, time1Raw,
                                      time2, time2Raw,
                                      time3, time3Raw));
    }

    @ParameterizedTest
    @ValueSource(strings = { date1Raw, date2Raw, date3Raw, dateFeb29LeapYearRaw,
                             dateInvalidApr31Raw, dateInvalidJun31Raw, dateInvalidSep31Raw, dateInvalidNov31Raw,
                             dateInvalidFeb30LeapYearRaw, dateInvalidFeb31LeapYearRaw,
                             dateInvalidFeb29NonLeapYearRaw, dateInvalidFeb30NonLeapYearRaw,
                             dateInvalidFeb31NonLeapYearRaw })
    void isParseableAsDateWorksCorrectlyForParseableDates(String parseableDate) {
        assertTrue(DateTimeParser.isParseableAsDate(parseableDate));
    }

    @ParameterizedTest
    @MethodSource("provideUnparseableDatesRaw")
    void isParseableAsDateWorksCorrectlyForUnparseableDates(String unparseableDate) {
        assertFalse(DateTimeParser.isParseableAsDate(unparseableDate));
    }
    
    @ParameterizedTest
    @ValueSource(strings = { time1Raw, time2Raw, time3Raw })
    void isParseableAsTimeWorksCorrectlyForParseableTimes(String parseableTime) {
        assertTrue(DateTimeParser.isParseableAsDate(parseableTime));
    }

    @ParameterizedTest
    @MethodSource("provideUnparseableTimesRaw")
    void isParseableAsDateWorksCorrectlyForUnparseableTimes(String unparseableTime) {
        assertFalse(DateTimeParser.isParseableAsTime(unparseableTime));
    }

    @ParameterizedTest
    @MethodSource
    void verifyDateIsValidWorksCorrectlyForValidDates(LocalDate date) {
        assertDoesNotThrow(() -> DateTimeParser.verifyDateIsValid(date));
    }
    
    @ParameterizedTest
    @MethodSource
    void verifyDateIsValidFailsCorrectlyForNonFebInvalidDates(LocalDate date) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> DateTimeParser.verifyDateIsValid(date));
        assertEquals(String.format("Date is invalid: Illegal value for day when month is %d: %d",
                                   date.getMonthValue(), date.getDayOfMonth()),
                     e.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void verifyDateIsValidFailsCorrectlyForFebInvalidDates(LocalDate date) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> DateTimeParser.verifyDateIsValid(date));
        assertEquals(String.format("Date is invalid: Illegal value for day when month is %d and year is %d: %d",
                                   date.getMonthValue(), date.getYear(), date.getDayOfMonth()),
                     e.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void parseToDateWorksCorrectlyWithValidParseableDates(String dateRaw, LocalDate date) {
        assertEquals(DateTimeParser.parseToDate(dateRaw), date);
    }

    @ParameterizedTest
    @MethodSource("provideUnparseableDatesRaw")
    void parseToDateFailsCorrectlyForUnparseableDates(String dateRaw) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> DateTimeParser.parseToDate(dateRaw));
        assertEquals(String.format("String %s cannot be parsed as date.", dateRaw), e.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void parseToDateFailsCorrectlyForNonFebInvalidParseableDates(String dateRaw, LocalDate date) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> DateTimeParser.parseToDate(dateRaw));
        assertEquals(String.format("Date is invalid: Illegal value for day when month is %d: %d",
                                   date.getMonthValue(), date.getDayOfMonth()),
                     e.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void parseToDateFailsCorrectlyForFebInvalidParseableDates(String dateRaw, LocalDate date) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> DateTimeParser.parseToDate(dateRaw));
        assertEquals(String.format(String.join(" ",
                                               "Date is invalid: Illegal value for day when",
                                               "month is %d and year is %d: %d"),
                                   date.getMonthValue(), date.getYear(), date.getDayOfMonth()),
                     e.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void parseToTimeWorksCorrectlyForParseableTimes(String timeRaw, LocalTime time) {
        assertEquals(DateTimeParser.parseToTime(timeRaw), time);
    }

    @ParameterizedTest
    @MethodSource("provideUnparseableTimesRaw")
    void parseToTimeFailsCorrectlyForUnparseableTimes(String timeRaw) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> DateTimeParser.parseToTime(timeRaw));
        assertEquals(String.format("String %s cannot be parsed as time.", timeRaw),
                     e.getMessage());
    }
    
    @ParameterizedTest
    @MethodSource
    void toStringForDateWorksCorrectly(LocalDate date, String dateRaw) {
        assertEquals(DateTimeParser.toString(date), dateRaw);
    }
    
    @ParameterizedTest
    @MethodSource
    void toStringForTimeWorksCorrectly(LocalTime time, String timeRaw) {
       assertEquals(DateTimeParser.toString(time), timeRaw);
    }
}
