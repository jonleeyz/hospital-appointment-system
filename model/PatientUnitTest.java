package model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import model.entities.id.PatientId;

class PatientUnitTest {
    private final String patient1IdRaw = "P1";
    private final String patient1Name = "Felicia Tan";
    private final int patient1Age = 25;
    private final String patient1GenderRaw = "F";
    private final Patient patient1 = new Patient(patient1IdRaw, patient1Name, patient1Age, patient1GenderRaw);

    @Test
    void constructorWorksCorrectlyTest() {
        PatientId patient1Id = new PatientId(patient1IdRaw);
        Patient.Gender patient1Gender = Patient.Gender.F;
        assertEquals(patient1.getId(), patient1Id);
        assertEquals(patient1.getName(), patient1Name);
        assertEquals(patient1.getAge(), patient1Age);
        assertEquals(patient1.getGender(), patient1Gender);
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, -2 })
    void constructorFailsCorrectlyOnInvalidAgeTest(int invalidPatientAge) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> new Patient(patient1IdRaw, patient1Name,
                                                     invalidPatientAge, patient1GenderRaw));
        assertEquals(String.format("Specified age [%d] cannot be negative.", invalidPatientAge),
                     e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "f", "m", "female", "male" })
    void constructorFailsCorrectlyOnInvalidFormatGenderTest(String invalidPatientGenderRaw) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> new Patient(patient1IdRaw, patient1Name,
                                                     patient1Age, invalidPatientGenderRaw));
        assertEquals(String.format("Missing implementation for specified gender: %s.", invalidPatientGenderRaw),
                     e.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = { "X", "NB", "invalid string", })
    void constructorFailsCorrectlyOnInvalidGenderTest(String invalidPatientGenderRaw) {
        Exception e = assertThrows(IllegalArgumentException.class,
                                   () -> new Patient(patient1IdRaw, patient1Name,
                                                     patient1Age, invalidPatientGenderRaw));
        assertEquals(String.format("Missing implementation for specified gender: %s.", invalidPatientGenderRaw),
                     e.getMessage());

    }

    @Test
    void equalsOverrideWorksCorrectlyTest() {
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
