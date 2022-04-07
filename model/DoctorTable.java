package model;

import java.util.HashMap;

/**
 * A table that stores Doctor objects.
 * - Each Doctor is uniquely indexed by its respective DoctorId.
 * - 2 Doctors with different details cannot share the same DoctorId.
 */
class DoctorTable {
    private HashMap<DoctorId, Doctor> table = new HashMap<DoctorId, Doctor>();

    boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Checks if a Doctor with DoctorId doctorId exists in the DoctorTable.
     */ 
    boolean contains(String doctorId) {
        return table.containsKey(new DoctorId(doctorId));
    }

    /**
     * Gets the Doctor with DoctorId doctorId.
     */ 
    Doctor get(String doctorId) {
        return table.get(new DoctorId(doctorId));
    }

    /**
     * Creates a new Doctor entry.
     * - @throws IllegalStateException if a Doctor object with DoctorId doctorId already exists.
     */
    void create(String doctorId, String doctorName) {
        if (contains(doctorId)) {
            throw new IllegalStateException(String.format("Doctor with DoctorId %s already exists.",
                                            doctorId));
        }

        table.put(new DoctorId(doctorId),
                  new Doctor(doctorId, doctorName));
    }

    /**
     * Verifies that the details provided are consistent with the Doctor in the DoctorTable.
     * - @throws IllegalStateException if no Doctor object with DoctorId doctorId exists.
     */
    boolean verifyDetails(String doctorId, String doctorName) throws IllegalStateException {
        Doctor dummyDoctor = new Doctor(doctorId, doctorName);
        try {
            return get(doctorId).equals(dummyDoctor);
        } catch (NullPointerException e) {
            throw new IllegalStateException(String.format("Doctor with DoctorId %s does not exist.", doctorId));
        }
    }

}
