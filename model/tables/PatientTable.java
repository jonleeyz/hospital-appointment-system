package model.tables;

import java.util.HashMap;

import model.entities.Patient;
import model.entities.id.PatientId;

/**
 * A table that stores Patient objects.
 * - Each Patient is uniquely indexed by its respective PatientId.
 * - 2 Patients with different details cannot share the same PatientId.
 */
public class PatientTable {
    private HashMap<PatientId, Patient> table = new HashMap<PatientId, Patient>();

    boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Checks if a Patient with PatientId patientId exists in the table.
     */ 
    public boolean contains(String patientId) {
        return table.containsKey(new PatientId(patientId));
    }

    /**
     * Gets the Patient with PatientId patientId.
     */ 
    public Patient get(String patientId) {
        return table.get(new PatientId(patientId));
    }

    /**
     * Verifies that the details provided are consistent with the Patient in the table.
     * - @throws IllegalStateException if no Patient object with PatientId patientId exists.
     */
    public boolean verifyDetails(String patientId, String patientName, int patientAge, String patientGender)
        throws IllegalStateException {
        if (contains(patientId)) {
            return get(patientId).equals(new Patient(patientId, patientName, patientAge, patientGender));
        } else {
            throw new IllegalStateException(String.format("Patient with id %s does not exist.", patientId));
        }
    }

    /**
     * Creates a new Patient entry if needed.
     * - @throws IllegalStateException if a Patient object with PatientId patientId already exists abd the specified
     *   method arguments do not match the existing entity's details.
     */
    public void put(String patientId, String patientName, int patientAge, String patientGender)
        throws IllegalStateException {
        if (!(verifyDetails(patientId, patientName, patientAge, patientGender))) {
            Patient existingPatient = get(patientId);
            throw new IllegalStateException(String.format(String.join(" ",
                                                                      "Patient with id %s already exists,",
                                                                      "with different details:",
                                                                      "name (%s:%s), age (%s:%s), gender (%s:%s)"),
                                                          patientId,
                                                          existingPatient.getName(), patientName,
                                                          existingPatient.getAge(), patientAge,
                                                          existingPatient.getGender(), patientGender));
        }

        table.put(new PatientId(patientId),
                  new Patient(patientId, patientName, patientAge, patientGender));
    }
}
