package model.tables;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorTableUnitTest {
    private DoctorTable table = null;
    private final String doctorId1 = "D1";
    private final String doctorId2 = "D2";
    private final String doctorName1 = "Tony Tuchel";
    private final String doctorName2 = "Melvin Merkle";

    @BeforeEach
    void init() {
        table = new DoctorTable();
    }

    @Test
    void constructorWorksCorrectly() {
        assertTrue(table.isEmpty());
    }

    @Test
    void verifyRecordWorksCorrectly() {
        assertTrue(table.isEmpty());

        table.put(doctorId1, doctorName1);
        assertTrue(table.verifyRecord(doctorId1, doctorName1));
        assertFalse(table.verifyRecord(doctorId1, doctorName2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(doctorId2, doctorName1));
        assertEquals(String.format("Doctor with id %s does not exist.", doctorId2), e1.getMessage());
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(doctorId2, doctorName2));
        assertEquals(String.format("Doctor with id %s does not exist.", doctorId2), e2.getMessage());

        table.put(doctorId2, doctorName2);
        assertTrue(table.verifyRecord(doctorId2, doctorName2));
        assertFalse(table.verifyRecord(doctorId2, doctorName1));
        String doctorId3 = "D3";
        Exception e3 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(doctorId3, doctorName1));
        assertEquals(String.format("Doctor with DoctorId %s does not exist.", doctorId3), e3.getMessage());
        Exception e4 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyRecord(doctorId3, doctorName2));
        assertEquals(String.format("Doctor with DoctorId %s does not exist.", doctorId3), e4.getMessage());
    }

    @Test
    void putWorksCorrectly() {
        assertTrue(table.isEmpty());

        // put doctor1
        table.put(doctorId1, doctorName1);
        assertTrue(table.contains(doctorId1));
        assertFalse(table.contains(doctorId2));

        // put doctor1 again
        table.put(doctorId1, doctorName1);
        assertTrue(table.contains(doctorId1));
        assertFalse(table.contains(doctorId2));

        // put doctor1 with non-matching details
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.put(doctorId1, doctorName2));
        assertEquals(String.format(String.join(" ",
                                               "Doctor with id %s already exists with different details:",
                                               "name (%s:%s)"),
                                   doctorId1, doctorName1, doctorName2),
                     e1.getMessage());

        // put doctor2
        table.put(doctorId2, doctorName2);
        assertTrue(table.contains(doctorId1));
        assertTrue(table.contains(doctorId2));

        // put doctor2 again
        table.put(doctorId2, doctorName2);
        assertTrue(table.contains(doctorId1));
        assertTrue(table.contains(doctorId2));

        // put doctor2 with non-matching details
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.put(doctorId2, doctorName1));
        assertEquals(String.format(String.join(" ",
                                               "Doctor with id %s already exists with different details:",
                                               "name (%s:%s)"),
                                   doctorId2, doctorName2, doctorName1),
                     e2.getMessage());
    }
}
