package model.entities;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import model.entities.id.*;

class AppointmentUnitTest {
    private final String appointment1Id = "A5";
    private final String doctor1Id = "A1";
    private final String patient1Id = "B2";
    private final String date1Raw = "08032018";
    private final String time1Raw = "09:00:00";
    private final Appointment appointment1 = new Appointment(appointment1Id, patient1Id, doctor1Id,
                                                             date1Raw, time1Raw);
    // TODO: standardize test cases with DateTimeParserUnitTest

    @Test
    void constructorWorksCorrectlyTest() {
        assertEquals(appointment1.getAppointmentId(), new AppointmentId(appointment1Id));
        assertEquals(appointment1.getDoctorId(), new DoctorId(doctor1Id));
        assertEquals(appointment1.getPatientId(), new PatientId(patient1Id));
        assertEquals(appointment1.getDate(), LocalDate.of(2018, 3, 8));
        assertEquals(appointment1.getTime(), LocalTime.of(9, 0, 0));
        assertEquals(appointment1.getDuration(), 60);
    }

    @ParameterizedTest
    @ValueSource(strings = { "32032018", "00032018", "08132018", "08002018" })
    void constructorFailsCorrectlyOnInvalidlyFormattedDateTest(String unparseableDate) {
        assertThrows(IllegalArgumentException.class,
                     () -> new Appointment(appointment1Id, patient1Id, doctor1Id, unparseableDate, time1Raw));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "random string", "08/03/2018", "080318", "8 Jul 2018", "8 July 2018" })
    void constructorFailsCorrectlyOnUnparseableStringDateTest(String unparseableDate) {
        assertThrows(IllegalArgumentException.class,
                     () -> new Appointment(appointment1Id, patient1Id, doctor1Id, unparseableDate, time1Raw));
    }

    @ParameterizedTest
    @ValueSource(strings = { "29022018", "30022020", "31042018", "31112018" })
    @Disabled
    void constructorFailsCorrectlyOnParseableButInvalidDateTest(String invalidDate) {
        assertThrows(IllegalArgumentException.class,
                     () -> new Appointment(appointment1Id, patient1Id, doctor1Id, invalidDate, time1Raw));
    }

    @ParameterizedTest
    @ValueSource(strings = { "09:60:00", "09:00:60", "24:01:00", "25:00:00" })
    void constructorFailsCorrectlyOnInvalidlyFormattedTimeTest(String unparseableTime) {
        assertThrows(IllegalArgumentException.class,
                     () -> new Appointment(appointment1Id, patient1Id, doctor1Id, date1Raw, unparseableTime));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "random string", "09.00.00", "09,00,00", "09 00 00", "09:00", "09.00", "9 pm",
                             "9.00 pm", "9:00 pm", "009:00:00" })
    void constructorFailsCorrectlyOnUnparseableStringTimeTest(String unparseableTime) {
        assertThrows(IllegalArgumentException.class,
                     () -> new Appointment(appointment1Id, patient1Id, doctor1Id, date1Raw, unparseableTime));
    }

    @Test
    void equalsOverrideWorksCorrectlyTest() {
        String appointment2Id = "X10";
        String doctor2Id = "A10";
        String patient2Id = "B5";
        String date2Raw = "08042018";
        String time2Raw = "10:00:00";

        Appointment appointment3 = new Appointment(appointment1Id, patient1Id, doctor1Id, date1Raw, time1Raw);
        Appointment appointment2 = new Appointment(appointment2Id, patient1Id, doctor1Id, date1Raw, time1Raw);
        Appointment appointment4 = new Appointment(appointment1Id, patient2Id, doctor1Id, date1Raw, time1Raw);
        Appointment appointment5 = new Appointment(appointment1Id, patient1Id, doctor2Id, date1Raw, time1Raw);
        Appointment appointment6 = new Appointment(appointment1Id, patient1Id, doctor1Id, date2Raw, time1Raw);
        Appointment appointment7 = new Appointment(appointment1Id, patient1Id, doctor1Id, date1Raw, time2Raw);

        assertEquals(appointment1, appointment3);
        assertNotEquals(appointment1, appointment2);
        assertNotEquals(appointment1, appointment4);
        assertNotEquals(appointment1, appointment5);
        assertNotEquals(appointment1, appointment6);
        assertNotEquals(appointment1, appointment7);
    }
}
