package model.tables;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientTableUnitTest {
    private PatientTable table = null;
    private final String patientId1 = "P1";
    private final String patientId2 = "P2";
    private final String patientName1 = "Theresa Margaret";
    private final String patientName2 = "Mitch Trump";
    private final int patientAge1 = 10;
    private final int patientAge2 = 81;
    private final String patientGender1 = "F";
    private final String patientGender2 = "M";

    @BeforeEach
    void init() {
        table = new PatientTable();
    }

    @Test
    void constructorWorksCorrectly() {
        assertTrue(table.isEmpty());
    }

    @Test
    void verifyRecordWorksCorrectly() {
        assertTrue(table.isEmpty());

        table.put(patientId1, patientName1, patientAge1, patientGender1);
        assertTrue(table.verifyRecord(patientId1, patientName1, patientAge1, patientGender1));
        assertFalse(table.verifyRecord(patientId1, patientName2, patientAge1, patientGender1));
        assertFalse(table.verifyRecord(patientId1, patientName1, patientAge2, patientGender1));
        assertFalse(table.verifyRecord(patientId1, patientName1, patientAge1, patientGender2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(patientId2, patientName1, patientAge1, patientGender1));
        assertEquals(String.format("Patient with id %s does not exist.", patientId2), e1.getMessage());
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(patientId2, patientName2, patientAge2, patientGender2));
        assertEquals(String.format("Patient with id %s does not exist.", patientId2), e2.getMessage());

        table.put(patientId2, patientName2, patientAge2, patientGender2);
        assertTrue(table.verifyRecord(patientId2, patientName2, patientAge2, patientGender2));
        assertFalse(table.verifyRecord(patientId2, patientName1, patientAge2, patientGender2));
        assertFalse(table.verifyRecord(patientId2, patientName2, patientAge1, patientGender2));
        assertFalse(table.verifyRecord(patientId2, patientName2, patientAge2, patientGender1));
        String patientId3 = "P3";
        Exception e3 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(patientId3, patientName1, patientAge1, patientGender1));
        assertEquals(String.format("Patient with id %s does not exist.", patientId3), e3.getMessage());
        Exception e4 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(patientId3, patientName2, patientAge2, patientGender2));
        assertEquals(String.format("Patient with id %s does not exist.", patientId3), e4.getMessage());
    }

    @Test
    void putWorksCorrectly() {
        assertTrue(table.isEmpty());

        // put patient1
        table.put(patientId1, patientName1, patientAge1, patientGender1);
        assertTrue(table.contains(patientId1));
        assertFalse(table.contains(patientId2));

        // put patient1 again
        table.put(patientId1, patientName1, patientAge1, patientGender1);
        assertTrue(table.contains(patientId1));
        assertFalse(table.contains(patientId2));

        // put patient1 with non-matching details
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.put(patientId1, patientName2, patientAge1, patientGender1));
        assertEquals(String.format(String.join(" ",
                                               "Patient with id %s already exists with different details:",
                                               "name (%s:%s), age (%s:%s), gender (%s:%s)"),
                                   patientId1, patientName1, patientName2,
                                   patientAge1, patientAge1, patientGender1, patientGender1),
                     e1.getMessage());

        // put patient2
        table.put(patientId2, patientName2, patientAge2, patientGender2);
        assertTrue(table.contains(patientId1));
        assertTrue(table.contains(patientId2));

        // put patient2 again
        table.put(patientId2, patientName2, patientAge2, patientGender2);
        assertTrue(table.contains(patientId1));
        assertTrue(table.contains(patientId2));

        // put patient2 with non-matching details
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.put(patientId2, patientName1, patientAge2, patientGender2));
        assertEquals(String.format(String.join(" ",
                                               "Patient with id %s already exists with different details:",
                                               "name (%s:%s), age (%s:%s), gender (%s:%s)"),
                                   patientId2, patientName2, patientName1,
                                   patientAge2, patientAge2, patientGender2, patientGender2),
                     e2.getMessage());
    }
}
