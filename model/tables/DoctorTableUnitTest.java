package model.tables;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoctorTableUnitTest {
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
    void createWorksCorrectly() {
        assertFalse(table.contains(doctorId1));

        table.create(doctorId1, doctorName1);
        assertTrue(table.contains(doctorId1));
        assertFalse(table.contains(doctorId2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.create(doctorId1, doctorName1));
        assertEquals(String.format("Doctor with DoctorId %s already exists.", doctorId1),
                     e1.getMessage());

        table.create(doctorId2, doctorName2);
        assertTrue(table.contains(doctorId1));
        assertTrue(table.contains(doctorId2));
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.create(doctorId2, doctorName2));
        assertEquals(String.format("Doctor with DoctorId %s already exists.", doctorId2),
                     e2.getMessage());
    }

    @Test
    void verifyDetailsWorksCorrectly() {
        table.create(doctorId1, doctorName1);
        assertTrue(table.verifyDetails(doctorId1, doctorName1));
        assertFalse(table.verifyDetails(doctorId1, doctorName2));
        Exception e1 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(doctorId2, doctorName1));
        assertEquals(String.format("Doctor with DoctorId %s does not exist.", doctorId2),
                     e1.getMessage());
        Exception e2 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(doctorId2, doctorName2));
        assertEquals(String.format("Doctor with DoctorId %s does not exist.", doctorId2),
                     e2.getMessage());

        table.create(doctorId2, doctorName2);
        assertTrue(table.verifyDetails(doctorId2, doctorName2));
        assertFalse(table.verifyDetails(doctorId2, doctorName1));

        String doctorId3 = "D3";
        Exception e3 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(doctorId3, doctorName1));
        assertEquals(String.format("Doctor with DoctorId %s does not exist.", doctorId3),
                     e3.getMessage());
        Exception e4 = assertThrows(IllegalStateException.class,
                                    () -> table.verifyDetails(doctorId3, doctorName2));
        assertEquals(String.format("Doctor with DoctorId %s does not exist.", doctorId3),
                     e4.getMessage());
    }
}
