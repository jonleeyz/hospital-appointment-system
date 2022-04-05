package model;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class AppointmentIdUnitTest {
    private String x;
    private String y;
    private AppointmentId appointmentId1;

    @BeforeEach
    void init() {
        x = "x";
        y = "y";
        appointmentId1 = new AppointmentId(x);
    }
    
    /**
     * Tests the AppointmentId constructor.
     * - Unit test is trivial if the format of an AppointmentId is unrestricted.
     * - Nevertheless, to faciliate TDD, test will be left in place in case AppointmentId implementation changes.
     */
    @Test
    void ConstructorTest() {
        assertEquals(appointmentId1.id, "x");

        AppointmentId appointmentIdBlank = new AppointmentId("");
        assertEquals(appointmentIdBlank.id, "");
    }

    @Test
    void EqualsOverrideTest() {
        AppointmentId appointmentId2 = new AppointmentId(x);
        Object appointmentId3 = new AppointmentId(x);
        AppointmentId appointmentId4 = new AppointmentId(y);

        assertEquals(appointmentId1, appointmentId2);
        assertEquals(appointmentId1, appointmentId3);
        assertNotEquals(appointmentId1, appointmentId4);
    }
}
