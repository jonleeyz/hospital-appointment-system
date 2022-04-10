package model;

import java.util.HashMap;

import model.entities.id.AppointmentId;
import model.entities.id.DoctorId;
import model.entities.id.PatientId;

class AppointmentTable {
    private HashMap<AppointmentId, Appointment> table = new HashMap<AppointmentId, Appointment>();
    
    boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Checks if a Appointment with AppointmentId appointmentId exists in the table.
     */ 
    boolean contains(String appointmentId) {
        return table.containsKey(new AppointmentId(appointmentId));
    }

    /**
     * Gets the Appointment with AppointmentId appointmentId.
     */ 
    Appointment get(String appointmentId) {
        return table.get(new AppointmentId(appointmentId));
    }

    /**
     * Creates a new Appointment entry.
     * - @throws IllegalStateException if a Appointment object with AppointmentId appointmentId already exists.
     */
    void create(String appointmentIdRaw, PatientId patientId, DoctorId doctorId, String date, String time)
        throws IllegalStateException {
        if (contains(appointmentIdRaw)) {
            throw new IllegalStateException(String.format("Appointment with AppointmentId %s already exists.",
                                            appointmentIdRaw));
        }

        AppointmentId appointmentId = new AppointmentId(appointmentIdRaw);
        table.put(appointmentId,
                  new Appointment(appointmentId, patientId, doctorId, date, time));
    }

    /**
     * Verifies that the details provided are consistent with the Appointment in the table.
     * - @throws IllegalStateException if no Appointment object with AppointmentId appointmentId exists.
     */
    boolean verifyDetails(String appointmentIdRaw, PatientId patientId, DoctorId doctorId, String date, String time)
        throws IllegalStateException {
        AppointmentId appointmentId = new AppointmentId(appointmentIdRaw);
        Appointment dummyAppointment = new Appointment(appointmentId, patientId, doctorId, date, time);
        try {
            return get(appointmentIdRaw).equals(dummyAppointment);
        } catch (NullPointerException e) {
            throw new IllegalStateException(String.format("Appointment with AppointmentId %s does not exist.",
                                                          appointmentIdRaw));
        }
    }
}
