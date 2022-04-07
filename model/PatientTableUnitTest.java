package model;

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
    private final String patientGender2 = "N";

    @BeforeEach
    void init() {
        table = new PatientTable();
    }

    @Test
    void constructorWorksCorrectly() {
        assertTrue(table.isEmpty());
    }

    @Test
    void createWorksCorrectly() {
        assertFalse(table.contains(patientId1));

        table.create(patientId1, patientName1, patientAge1, patientGender1);
        assertTrue(table.contains(patientId1));
        assertFalse(table.contains(patientId2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.create(patientId1, patientName1, patientAge1, patientGender1));
        assertEquals(String.format("Patient with PatientId %s already exists.", patientId1),
                     e1.getMessage());

        table.create(patientId2, patientName2, patientAge2, patientGender2);
        assertTrue(table.contains(patientId1));
        assertTrue(table.contains(patientId2));
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.create(patientId2, patientName2, patientAge2, patientGender2));
        assertEquals(String.format("Patient with PatientId %s already exists.", patientId2),
                     e2.getMessage());
    }

    @Test
    void verifyDetailsWorksCorrectly() {
        table.create(patientId1, patientName1, patientAge1, patientGender1);
        assertTrue(table.verifyDetails(patientId1, patientName1, patientAge1, patientGender1));
        assertFalse(table.verifyDetails(patientId1, patientName2, patientAge1, patientGender1));
        assertFalse(table.verifyDetails(patientId1, patientName1, patientAge2, patientGender1));
        assertFalse(table.verifyDetails(patientId1, patientName1, patientAge1, patientGender2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(patientId2, patientName1, patientAge1, patientGender1));
        assertEquals(String.format("Patient with PatientId %s does not exist.", patientId2),
                     e1.getMessage());
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(patientId2, patientName2, patientAge2, patientGender2));
        assertEquals(String.format("Patient with PatientId %s does not exist.", patientId2),
                     e2.getMessage());

        table.create(patientId2, patientName2, patientAge2, patientGender2);
        assertTrue(table.verifyDetails(patientId2, patientName2, patientAge2, patientGender2));
        assertFalse(table.verifyDetails(patientId2, patientName1, patientAge2, patientGender2));
        assertFalse(table.verifyDetails(patientId2, patientName2, patientAge1, patientGender2));
        assertFalse(table.verifyDetails(patientId2, patientName2, patientAge2, patientGender1));

        String patientId3 = "P3";
        Exception e3 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(patientId3, patientName1, patientAge1, patientGender1));
        assertEquals(String.format("Patient with PatientId %s does not exist.", patientId3),
                     e3.getMessage());
        Exception e4 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(patientId3, patientName2, patientAge2, patientGender2));
        assertEquals(String.format("Patient with PatientId %s does not exist.", patientId3),
                     e4.getMessage());
    }
}
