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
     * - @throws IllegalStateException if no Doctor object with DoctorId doctorId exists.
     */
    public boolean verifyDetails(String doctorId, String doctorName) throws IllegalStateException {
        if (contains(doctorId)) {
            return get(doctorId).equals(new Doctor(doctorId, doctorName));
        } else {
            throw new IllegalStateException(String.format("Doctor with id %s does not exist.", doctorId));
        }
    }

    /**
     * Creates a new Doctor entry if needed.
     * - @throws IllegalStateException if a Doctor object with DoctorId doctorId already exists and the specified 
     *   method arguments do not match the existing entry's details.
     */
    public void put(String doctorId, String doctorName) throws IllegalStateException {
        if (!(verifyDetails(doctorId, doctorName))) {
            Doctor existingDoctor = get(doctorId);
            throw new IllegalStateException(String.format(String.join(" ",
                                                                      "Doctor with id %s already exists,",
                                                                      "with different details: name (%s:%s)"),
                                                          doctorId, existingDoctor.getName(), doctorName));
        }

        table.put(new DoctorId(doctorId),
                  new Doctor(doctorId, doctorName));
    }
}
