package model.entities.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class AppointmentIdUnitTest {
    private final String x = "x";
    private final String y = "y";
    private final AppointmentId appointmentId1 = new AppointmentId(x);

    /**
     * Tests the AppointmentId constructor.
     * - Unit test is trivial if the format of an AppointmentId is unrestricted.
     * - Nevertheless, to faciliate TDD, test will be left in place in case AppointmentId implementation changes.
     */
    @Test
    void constructorWorksCorrectlyTest() {
        assertEquals(appointmentId1.id, "x");

        AppointmentId appointmentIdBlank = new AppointmentId("");
        assertEquals(appointmentIdBlank.id, "");
    }

    @Test
    void equalsOverrideWorksCorrectlyTest() {
        AppointmentId appointmentId2 = new AppointmentId(x);
        Object appointmentId3 = new AppointmentId(x);
        AppointmentId appointmentId4 = new AppointmentId(y);

        assertEquals(appointmentId1, appointmentId2);
        assertEquals(appointmentId1, appointmentId3);
        assertNotEquals(appointmentId1, appointmentId4);
    }
}
