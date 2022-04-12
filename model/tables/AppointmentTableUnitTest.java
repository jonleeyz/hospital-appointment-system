package model.tables;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppointmentTableUnitTest {
    private AppointmentTable table = null;
    private final String appointmentId1 = "A1";
    private final String appointmentId2 = "A2";
    private final String patientId1 = "P1";
    private final String patientId2 = "P2";
    private final String doctorId1 = "D1";
    private final String doctorId2 = "D2";
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
    void verifyRecordWorksCorrectly() {
        assertTrue(table.isEmpty());

        table.put(appointmentId1, patientId1, doctorId1, date1, time1);
        assertTrue(table.verifyRecord(appointmentId1, patientId1, doctorId1, date1, time1));
        assertFalse(table.verifyRecord(appointmentId1, patientId2, doctorId1, date1, time1));
        assertFalse(table.verifyRecord(appointmentId1, patientId1, doctorId2, date1, time1));
        assertFalse(table.verifyRecord(appointmentId1, patientId1, doctorId1, date2, time1));
        assertFalse(table.verifyRecord(appointmentId1, patientId1, doctorId1, date1, time2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(appointmentId2, patientId1, doctorId1, date1, time1));
        assertEquals(String.format("Appointment with id %s does not exist.", appointmentId2), e1.getMessage());
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(appointmentId2, patientId2, doctorId2, date2, time2));
        assertEquals(String.format("Appointment with id %s does not exist.", appointmentId2), e2.getMessage());

        table.put(appointmentId2, patientId2, doctorId2, date2, time2);
        assertTrue(table.verifyRecord(appointmentId2, patientId2, doctorId2, date2, time2));
        assertFalse(table.verifyRecord(appointmentId2, patientId1, doctorId2, date2, time2));
        assertFalse(table.verifyRecord(appointmentId2, patientId2, doctorId1, date2, time2));
        assertFalse(table.verifyRecord(appointmentId2, patientId2, doctorId2, date1, time2));
        assertFalse(table.verifyRecord(appointmentId2, patientId2, doctorId2, date2, time1));
        String appointmentId3 = "A3";
        Exception e3 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(appointmentId3, patientId1, doctorId1, date1, time1));
        assertEquals(String.format("Appointment with id %s does not exist.", appointmentId3), e3.getMessage());
        Exception e4 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(appointmentId3, patientId2, doctorId2, date2, time2));
        assertEquals(String.format("Appointment with id %s does not exist.", appointmentId3), e4.getMessage());
    }

    @Test
    void putWorksCorrectly() {
        assertTrue(table.isEmpty());
        
        // put patient1
        table.put(appointmentId1, patientId1, doctorId1, date1, time1);
        assertTrue(table.contains(appointmentId1));
        assertFalse(table.contains(appointmentId2));

        // put patient1 again
        table.put(appointmentId1, patientId1, doctorId1, date1, time1);
        assertTrue(table.contains(appointmentId1));
        assertFalse(table.contains(appointmentId2));

        // put patient1 with non-matching details
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.put(appointmentId1, patientId2, doctorId1, date1, time1));
        assertEquals(String.format(String.join(" ",
                                               "Appointment with id %s already exists with different details:",
                                               "patientId (%s:%s), doctorId (%s:%s), date (%s:%s), time (%s:%s)"),
                                   appointmentId1, patientId1, patientId2, doctorId1, doctorId1,
                                   date1, date1, time1, time1),
                     e1.getMessage());

        table.put(appointmentId2, patientId2, doctorId2, date2, time2);
        assertTrue(table.contains(appointmentId1));
        assertTrue(table.contains(appointmentId2));
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.put(appointmentId2, patientId1, doctorId2, date2, time2));
        assertEquals(String.format(String.join(" ",
                                               "Appointment with id %s already exists with different details:",
                                               "patientId (%s:%s), doctorId (%s:%s), date (%s:%s), time (%s:%s)"),
                                   appointmentId2, patientId2, patientId1, doctorId2, doctorId2,
                                   date2, date2, time2, time2),
                     e2.getMessage());
    }
}
