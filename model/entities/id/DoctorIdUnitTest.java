package model.entities.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class DoctorIdUnitTest {
    private final String x = "x";
    private final String y = "y";
    private final DoctorId doctorId1 = new DoctorId(x);

    /**
     * Tests the DoctorId constructor.
     * - Unit test is trivial if the format of a DoctorId is unrestricted.
     * - Nevertheless, to faciliate TDD, test will be left in place in case DoctorId implementation changes.
     */
    @Test
    void constructorWorksCorrectlyTest() {
        assertEquals(doctorId1.id, "x");

        DoctorId doctorIdBlank = new DoctorId("");
        assertEquals(doctorIdBlank.id, "");
    }

    @Test
    void equalsOverrideWorksCorrectlyTest() {
        DoctorId doctorId2 = new DoctorId(x);
        Object doctorId3 = new DoctorId(x);
        DoctorId doctorId4 = new DoctorId(y);

        assertEquals(doctorId1, doctorId2);
        assertEquals(doctorId1, doctorId3);
        assertNotEquals(doctorId1, doctorId4);
    }
}
