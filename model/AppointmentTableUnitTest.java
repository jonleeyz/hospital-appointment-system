package model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentTableUnitTest {
    private AppointmentTable table = null;
    private final String appointmentId1Raw = "A1";
    private final String appointmentId2Raw = "A2";
    private final PatientId patientId1 = new PatientId("P1");
    private final PatientId patientId2 = new PatientId("P2");
    private final DoctorId doctorId1 = new DoctorId("D1");
    private final DoctorId doctorId2 = new DoctorId("D2");
    private final String date1 = "18032018";
    private final String date2 = "19032018";
    private final String time1 = "09:00:00";
    private final String time2 = "10:00:00";

    @BeforeEach
    void init() {
        table = new AppointmentTable();
    }

    @Test
    void constructorWorksCorrectly() {
        assertTrue(table.isEmpty());
    }

    @Test
    void createWorksCorrectly() {
        assertFalse(table.contains(appointmentId1Raw));

        table.create(appointmentId1Raw, patientId1, doctorId1, date1, time1);
        assertTrue(table.contains(appointmentId1Raw));
        assertFalse(table.contains(appointmentId2Raw));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.create(appointmentId1Raw, patientId1, doctorId1, date1, time1));
        assertEquals(String.format("Appointment with AppointmentId %s already exists.", appointmentId1Raw),
                     e1.getMessage());

        table.create(appointmentId2Raw, patientId2, doctorId2, date2, time2);
        assertTrue(table.contains(appointmentId1Raw));
        assertTrue(table.contains(appointmentId2Raw));
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.create(appointmentId2Raw, patientId2, doctorId2, date2, time2));
        assertEquals(String.format("Appointment with AppointmentId %s already exists.", appointmentId2Raw),
                     e2.getMessage());
    }

    @Test
    void verifyDetailsWorksCorrectly() {
        table.create(appointmentId1Raw, patientId1, doctorId1, date1, time1);
        assertTrue(table.verifyDetails(appointmentId1Raw, patientId1, doctorId1, date1, time1));
        assertFalse(table.verifyDetails(appointmentId1Raw, patientId2, doctorId1, date1, time1));
        assertFalse(table.verifyDetails(appointmentId1Raw, patientId1, doctorId2, date1, time1));
        assertFalse(table.verifyDetails(appointmentId1Raw, patientId1, doctorId1, date2, time1));
        assertFalse(table.verifyDetails(appointmentId1Raw, patientId1, doctorId1, date1, time2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(appointmentId2Raw, patientId1, doctorId1, date1, time1));
        assertEquals(String.format("Appointment with AppointmentId %s does not exist.", appointmentId2Raw),
                     e1.getMessage());
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(appointmentId2Raw, patientId2, doctorId2, date2, time2));
        assertEquals(String.format("Appointment with AppointmentId %s does not exist.", appointmentId2Raw),
                     e2.getMessage());

        table.create(appointmentId2Raw, patientId2, doctorId2, date2, time2);
        assertTrue(table.verifyDetails(appointmentId2Raw, patientId2, doctorId2, date2, time2));
        assertFalse(table.verifyDetails(appointmentId2Raw, patientId1, doctorId2, date2, time2));
        assertFalse(table.verifyDetails(appointmentId2Raw, patientId2, doctorId1, date2, time2));
        assertFalse(table.verifyDetails(appointmentId2Raw, patientId2, doctorId2, date1, time2));
        assertFalse(table.verifyDetails(appointmentId2Raw, patientId2, doctorId2, date2, time1));

        String appointmentId3Raw = "P3";
        Exception e3 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(appointmentId3Raw, patientId1, doctorId1, date1, time1));
        assertEquals(String.format("Appointment with AppointmentId %s does not exist.", appointmentId3Raw),
                     e3.getMessage());
        Exception e4 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(appointmentId3Raw, patientId2, doctorId2, date2, time2));
        assertEquals(String.format("Appointment with AppointmentId %s does not exist.", appointmentId3Raw),
                     e4.getMessage());
    }
}
