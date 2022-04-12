package model.tables;

import java.util.HashMap;

import model.entities.Doctor;
import model.entities.id.DoctorId;

/**
 * A table that stores Doctor objects.
 * - Each Doctor is uniquely indexed by its respective DoctorId.
 * - 2 Doctors with different details cannot share the same DoctorId.
 */
public class DoctorTable {
    private HashMap<DoctorId, Doctor> table = new HashMap<DoctorId, Doctor>();

    boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Checks if a Doctor with DoctorId doctorId exists in the table.
     */ 
    public boolean contains(String doctorId) {
        return table.containsKey(new DoctorId(doctorId));
    }

    /**
     * Gets the Doctor with DoctorId doctorId.
     */ 
    public Doctor get(String doctorId) {
        return table.get(new DoctorId(doctorId));
    }

    /**
     * Verifies that the details provided are consistent with the Doctor in the table.
     * - @throws IllegalArgumentException if no Doctor object with DoctorId doctorId exists.
     */
    public boolean verifyRecord(String doctorId, String doctorName) throws IllegalArgumentException {
        if (contains(doctorId)) {
            return get(doctorId).equals(new Doctor(doctorId, doctorName));
        } else {
            throw new IllegalArgumentException(String.format("Doctor with id %s does not exist.", doctorId));
        }
    }

    /**
     * Creates a new Doctor entry if needed.
     * - @throws IllegalStateException if a Doctor object with DoctorId doctorId already exists and the specified 
     *   method arguments do not match the existing entry's details.
     */
    public void put(String doctorId, String doctorName) throws IllegalStateException {
        try {
            if (!(verifyRecord(doctorId, doctorName))) {
                Doctor existingDoctor = get(doctorId);
                throw new IllegalStateException(String.format(String.join(" ",
                                                                          "Doctor with id %s already exists",
                                                                          "with different details: name (%s:%s)"),
                                                              doctorId, existingDoctor.getName(), doctorName));
            }
        } catch (IllegalArgumentException iae) {
            assert(String.format("Doctor with id %s does not exist.", doctorId).equals(iae.getMessage()));
        }

        table.put(new DoctorId(doctorId), new Doctor(doctorId, doctorName));
    }
}
