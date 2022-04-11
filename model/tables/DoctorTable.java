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
     * Creates a new Doctor entry.
     * - @throws IllegalStateException if a Doctor object with DoctorId doctorId already exists.
     */
    public void create(String doctorId, String doctorName) throws IllegalStateException {
        if (contains(doctorId)) {
            throw new IllegalStateException(String.format("Doctor with DoctorId %s already exists.",
                                            doctorId));
        }

        table.put(new DoctorId(doctorId),
                  new Doctor(doctorId, doctorName));
    }

    /**
     * Verifies that the details provided are consistent with the Doctor in the table.
     * - @throws IllegalStateException if no Doctor object with DoctorId doctorId exists.
     */
    public boolean verifyDetails(String doctorId, String doctorName) throws IllegalStateException {
        Doctor dummyDoctor = new Doctor(doctorId, doctorName);
        try {
            return get(doctorId).equals(dummyDoctor);
        } catch (NullPointerException e) {
            throw new IllegalStateException(String.format("Doctor with DoctorId %s does not exist.", doctorId));
        }
    }
}
