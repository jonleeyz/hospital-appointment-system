package model;

import java.util.HashMap;

/**
 * A table that stores Patient objects.
 * - Each Patient is uniquely indexed by its respective PatientId.
 * - 2 Patients with different details cannot share the same PatientId.
 */
class PatientTable {
    private HashMap<PatientId, Patient> table = new HashMap<PatientId, Patient>();

    boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Checks if a Patient with PatientId PatientId exists in the PatientTable.
     */ 
    boolean contains(String patientId) {
        return table.containsKey(new PatientId(patientId));
    }

    /**
     * Gets the Patient with PatientId patientId.
     */ 
    Patient get(String patientId) {
        return table.get(new PatientId(patientId));
    }

    /**
     * Creates a new Patient entry.
     * - @throws IllegalStateException if a Patient object with PatientId patientId already exists.
     */
    void create(String patientId, String patientName, int patientAge, String patientGender) {
        if (contains(patientId)) {
            throw new IllegalStateException(String.format("Patient with PatientId %s already exists.",
                                            patientId));
        }

        table.put(new PatientId(patientId),
                  new Patient(patientId, patientName, patientAge, patientGender));
    }

    /**
     * Verifies that the details provided are consistent with the Patient in the PatientTable.
     * - @throws IllegalStateException if no Patient object with PatientId patientId exists.
     */
    boolean verifyDetails(String patientId, String patientName, int patientAge, String patientGender)
        throws IllegalStateException {
        Patient dummyPatient = new Patient(patientId, patientName, patientAge, patientGender);
        try {
            return get(patientId).equals(dummyPatient);
        } catch (NullPointerException e) {
            throw new IllegalStateException(String.format("Patient with PatientId %s does not exist.", patientId));
        }
    }

}
