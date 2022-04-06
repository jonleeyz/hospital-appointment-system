package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoctorUnitTest {
    private String doctor1IdRaw;
    private String doctor1Name;
    private Doctor doctor1;

    @BeforeEach
    void init() {
        doctor1IdRaw = "A1";
        doctor1Name = "Max Ong";
        doctor1 = new Doctor(doctor1IdRaw, doctor1Name);
    }

    @Test
    void constructorTest() {
        DoctorId doctor1Id = new DoctorId(doctor1IdRaw);
        assertEquals(doctor1.getId(), doctor1Id);
        assertEquals(doctor1.getName(), doctor1Name);
    }

    @Test
    void equalsOverrideTest() {
        String doctor2IdRaw = "B2";
        String doctor2Name = "Mark Chin";

        Doctor doctor3 = new Doctor(doctor1IdRaw, doctor1Name);
        Doctor doctor2 = new Doctor(doctor2IdRaw, doctor2Name);
        Doctor doctor4 = new Doctor(doctor1IdRaw, doctor2Name);
        Doctor doctor5 = new Doctor(doctor2IdRaw, doctor1Name);

        assertEquals(doctor1, doctor3);
        assertNotEquals(doctor1, doctor2);
        assertNotEquals(doctor1, doctor4);
        assertNotEquals(doctor1, doctor5);
    }
}
