package model.tables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.TestMethodOrder;

import model.entities.id.DoctorId;
import model.entities.id.AppointmentId;
import util.DateTimeParser;

// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IndexedAppointmentTableUnitTest {
    private static IndexedAppointmentTable<DoctorId> doctorTable;
    private static final DoctorId d1 = new DoctorId("D1");
    private static final DoctorId d2 = new DoctorId("D2");
    private static final AppointmentId a1 = new AppointmentId("A1");
    private static final AppointmentId a2 = new AppointmentId("A2");
    private static final AppointmentId a3 = new AppointmentId("A3");
    private static final AppointmentId a4 = new AppointmentId("A4");
    private static final LocalDate date1 = LocalDate.of(2000, 2, 29);
    private static final LocalDate date2 = LocalDate.of(2000, 12, 25);
    private static final LocalTime time1 = LocalTime.of(9, 0, 0);
    private static final LocalTime time2 = LocalTime.of(11, 0, 0);

    @BeforeEach
    void init() {
        doctorTable = new IndexedAppointmentTable<DoctorId>();
        assertTrue(doctorTable.isEmpty());
    }

    @Test
    @Order(1)
    void addWorksCorrectly() {
        doctorTable.add(d1, date1, time1, a1);
        assertTrue(doctorTable.containsAppointmentId(d1, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d2, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d1, date2, time1));
        assertFalse(doctorTable.containsAppointmentId(d1, date1, time2));

        doctorTable.add(d2, date1, time1, a2);
        assertTrue(doctorTable.containsAppointmentId(d2, date1, time1));
        assertTrue(doctorTable.containsAppointmentId(d1, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d2, date2, time1));
        assertFalse(doctorTable.containsAppointmentId(d2, date1, time2));

        doctorTable.add(d1, date1, time2, a3);
        assertTrue(doctorTable.containsAppointmentId(d1, date1, time2));
        assertFalse(doctorTable.containsAppointmentId(d2, date1, time2));
        assertTrue(doctorTable.containsAppointmentId(d1, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d1, date2, time1));

        doctorTable.add(d2, date2, time1, a4);
        assertTrue(doctorTable.containsAppointmentId(d2, date2, time1));
        assertFalse(doctorTable.containsAppointmentId(d1, date2, time1));
        assertTrue(doctorTable.containsAppointmentId(d2, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d2, date2, time2));

        // add duplicate without issue
        assertDoesNotThrow(() -> doctorTable.add(d1, date1, time1, a1));
        assertTrue(doctorTable.containsAppointmentId(d1, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d2, date1, time1));
        assertFalse(doctorTable.containsAppointmentId(d1, date2, time1));
        assertFalse(doctorTable.containsAppointmentId(d1, date1, time2));
    }

    @Test
    @Order(2)
    void getAppointmentIdWorksCorrectly() {
        Exception e1 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.getAppointmentId(d1, date1, time1));
        assertEquals(String.format("No matching appointment for given parameters: id %s, date %s, time %s.",
                                   d1, DateTimeParser.toString(date1), DateTimeParser.toString(time1)),
                     e1.getMessage());
        Exception e2 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.getAppointmentId(d2, date1, time1));
        assertEquals(String.format("No matching appointment for given parameters: id %s, date %s, time %s.",
                                   d2, DateTimeParser.toString(date1), DateTimeParser.toString(time1)),
                     e2.getMessage());
        Exception e3 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.getAppointmentId(d1, date1, time1));
        assertEquals(String.format("No matching appointment for given parameters: id %s, date %s, time %s.",
                                   d1, DateTimeParser.toString(date1), DateTimeParser.toString(time2)),
                     e3.getMessage());
        Exception e4 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.getAppointmentId(d2, date1, time1));
        assertEquals(String.format("No matching appointment for given parameters: id %s, date %s, time %s.",
                                   d2, DateTimeParser.toString(date2), DateTimeParser.toString(time1)),
                     e4.getMessage());

        doctorTable.add(d1, date1, time1, a1);
        assertEquals(a1, doctorTable.getAppointmentId(d1, date1, time1));
        doctorTable.add(d2, date1, time1, a2);
        assertEquals(a2, doctorTable.getAppointmentId(d2, date1, time1));
        doctorTable.add(d1, date1, time2, a3);
        assertEquals(a3, doctorTable.getAppointmentId(d1, date1, time1));
        doctorTable.add(d2, date2, time1, a4);
        assertEquals(a4, doctorTable.getAppointmentId(d2, date2, time1));
    }

    @Test
    @Order(2)
    void getAllWorksCorrectly() {
        Exception e1 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.getAll(d1, date1));
        assertEquals(String.format("No record of id %s in table.", d1), e1.getMessage());
        Exception e2 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.getAll(d2, date1));
        assertEquals(String.format("No record of id %s in table.", d2), e2.getMessage());

        doctorTable.add(d1, date1, time1, a1);
        doctorTable.add(d2, date1, time1, a2);
        doctorTable.add(d1, date1, time2, a3);
        doctorTable.add(d2, date2, time1, a4);

        HashMap<LocalTime, AppointmentId> d1date1 = new HashMap<LocalTime, AppointmentId>();
        HashMap<LocalTime, AppointmentId> d1date2 = new HashMap<LocalTime, AppointmentId>();
        HashMap<LocalTime, AppointmentId> d2date1 = new HashMap<LocalTime, AppointmentId>();
        HashMap<LocalTime, AppointmentId> d2date2 = new HashMap<LocalTime, AppointmentId>();
        d1date1.put(time1, a1);
        d1date1.put(time2, a3);
        d2date1.put(time1, a2);
        d2date2.put(time1, a4);
        assertEquals(d1date1, doctorTable.getAll(d1, date1));
        assertEquals(d1date2, doctorTable.getAll(d1, date2));
        assertEquals(d2date1, doctorTable.getAll(d2, date1));
        assertEquals(d2date2, doctorTable.getAll(d2, date2));
    }

    @Test
    @Order(2)
    void removeWorksCorrectly() {
        Exception e1 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d1, date1, time1, a1));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d1, date1, time1, a1),
                     e1.getMessage());
        Exception e2 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d2, date1, time1, a2));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d2, date1, time1, a2),
                     e2.getMessage());
        Exception e3 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d1, date1, time2, a3));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d1, date1, time2, a3),
                     e3.getMessage());
        Exception e4 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d2, date2, time1, a1));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d2, date2, time1, a1),
                     e4.getMessage());

        doctorTable.add(d1, date1, time1, a1);
        doctorTable.add(d2, date1, time1, a2);
        doctorTable.add(d1, date1, time2, a3);
        doctorTable.add(d2, date2, time1, a4);

        assertTrue(doctorTable.containsAppointmentId(d1, date1, time1));
        assertTrue(doctorTable.containsAppointmentId(d2, date1, time1));
        assertTrue(doctorTable.containsAppointmentId(d1, date1, time2));
        assertTrue(doctorTable.containsAppointmentId(d2, date2, time1));

        doctorTable.remove(d1, date1, time1, a1);
        assertFalse(doctorTable.containsAppointmentId(d1, date1, time1));
        Exception e5 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d1, date1, time1, a1));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d1, date1, time1, a1),
                     e5.getMessage());

        doctorTable.remove(d2, date1, time1, a2);
        assertFalse(doctorTable.containsAppointmentId(d2, date1, time1));
        Exception e6 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d2, date1, time1, a2));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d2, date1, time1, a2),
                     e6.getMessage());

        doctorTable.remove(d1, date1, time2, a3);
        assertFalse(doctorTable.containsAppointmentId(d1, date1, time2));
        Exception e7 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d1, date1, time2, a3));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d1, date1, time2, a3),
                     e7.getMessage());

        doctorTable.remove(d2, date2, time1, a4);
        assertFalse(doctorTable.containsAppointmentId(d2, date2, time1));
        Exception e8 = assertThrows(NoSuchElementException.class,
                                    () -> doctorTable.remove(d2, date2, time1, a1));
        assertEquals(String.format(String.join(" ",
                                               "No matching Appointment for given parameters:",
                                               "id %s, date %s, time %s, AppointmentId %s"),
                                   d2, date2, time1, a1),
                     e8.getMessage());
    }
}
