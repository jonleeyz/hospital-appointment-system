package model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientUnitTest {
    private String patient1IdRaw;
    private String patient1Name;
    private int patient1Age;
    private String patient1GenderRaw;
    private Patient patient1;
    
    @BeforeEach
    void init() {
        patient1IdRaw = "P1";
        patient1Name = "Felicia Tan";
        patient1Age = 25;
        patient1GenderRaw = "F";
        patient1 = new Patient(patient1IdRaw, patient1Name, patient1Age, patient1GenderRaw);
    }

    @Test
    void constructorTest() {
        PatientId patient1Id = new PatientId(patient1IdRaw);
        Patient.Gender patient1Gender = Patient.Gender.F;
        assertEquals(patient1.getId(), patient1Id);
        assertEquals(patient1.getName(), patient1Name);
        assertEquals(patient1.getAge(), patient1Age);
        assertEquals(patient1.getGender(), patient1Gender);

        int invalidPatientAge = -1;
        Exception ageException = assertThrows(IllegalArgumentException.class,
                                              () -> new Patient(patient1IdRaw, patient1Name,
                                                                invalidPatientAge, patient1GenderRaw));
        assertEquals("Specified age [-1] cannot be negative.", ageException.getMessage());

        String invalidPatientGenderRaw = "X";
        Exception genderException = assertThrows(IllegalArgumentException.class,
                                                 () -> new Patient(patient1IdRaw, patient1Name,
                                                                   patient1Age, invalidPatientGenderRaw));
        assertEquals("Missing implementation for specified gender: X.", genderException.getMessage());
    }

    @Test
    void equalsOverrideTest() {
        String patient2IdRaw = "V3";
        String patient2Name = "Hugo Lloris";
        int patient2Age = 71;
        String patient2GenderRaw = "M";

        Patient patient3 = new Patient(patient1IdRaw, patient1Name, patient1Age, patient1GenderRaw);
        Patient patient2 = new Patient(patient2IdRaw, patient2Name, patient2Age, patient2GenderRaw);
        Patient patient4 = new Patient(patient1IdRaw, patient2Name, patient1Age, patient1GenderRaw);
        Patient patient5 = new Patient(patient1IdRaw, patient1Name, patient2Age, patient1GenderRaw);
        Patient patient6 = new Patient(patient1IdRaw, patient1Name, patient1Age, patient2GenderRaw);

        assertEquals(patient1, patient3);
        assertNotEquals(patient1, patient2);
        assertNotEquals(patient1, patient4);
        assertNotEquals(patient1, patient5);
        assertNotEquals(patient1, patient6);
    }
}
