package model.entities.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PatientIdUnitTest {
    private final String x = "x";
    private final String y = "y";
    private final PatientId patientId1 = new PatientId(x);

    /**
     * Tests the PatientId constructor.
     * - Unit test is trivial if the format of a PatientId is unrestricted.
     * - Nevertheless, to faciliate TDD, test will be left in place in case PatientId implementation changes.
     */
    @Test
    void constructorWorksCorrectlyTest() {
        assertEquals(patientId1.id, "x");

        PatientId patientIdBlank = new PatientId("");
        assertEquals(patientIdBlank.id, "");
    }

    @Test
    void equalsOverrideWorksCorrectlyTest() {
        PatientId patientId2 = new PatientId(x);
        Object patientId3 = new PatientId(x);
        PatientId patientId4 = new PatientId(y);

        assertEquals(patientId1, patientId2);
        assertEquals(patientId1, patientId3);
        assertNotEquals(patientId1, patientId4);
    }
}
