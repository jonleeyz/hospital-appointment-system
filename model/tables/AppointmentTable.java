package model.tables;

import java.util.HashMap;

import model.entities.Appointment;
import model.entities.id.AppointmentId;

public class AppointmentTable {
    private HashMap<AppointmentId, Appointment> table = new HashMap<AppointmentId, Appointment>();
    
    boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Checks if a Appointment with AppointmentId appointmentId exists in the table.
     */ 
    public boolean contains(String appointmentId) {
        return table.containsKey(new AppointmentId(appointmentId));
    }

    /**
     * Gets the Appointment with AppointmentId appointmentId.
     */ 
    public Appointment get(String appointmentId) {
        return table.get(new AppointmentId(appointmentId));
    }

    /**
     * Verifies that the details provided are consistent with the Appointment in the table.
     * - @throws IllegalStateException if no Appointment object with AppointmentId appointmentId exists.
     */
    public boolean verifyDetails(String appointmentId, String patientId, String doctorId, String date, String time)
        throws IllegalArgumentException {
        if (contains(appointmentId)) {
            return get(appointmentId).equals(new Appointment(appointmentId, patientId, doctorId, date, time));
        } else {
            throw new IllegalStateException(String.format("Appointment with id %s does not exist.",
                                                             appointmentId));
        }
    }

    /**
     * Creates a new Appointment entry if needed.
     * - @throws IllegalStateException if a Appointment object with AppointmentId appointmentId already exists and the
     *   specified method arguments do not match the existing entry's details. 
     */
    public void put(String appointmentId, String patientId, String doctorId, String date, String time)
        throws IllegalStateException {
        if (!(verifyDetails(appointmentId, patientId, doctorId, date, time))) {
            Appointment existingAppointment = get(appointmentId);
            throw new IllegalStateException(String.format(String.join(" ",
                                                                      "Appointment with id %s already exists,",
                                                                      "with different details:",
                                                                      "patientId (%s:%s), doctorId (%s:%s),",
                                                                      "date (%s:%s), time (%s:%s)"),
                                                          appointmentId,
                                                          existingAppointment.getDoctorId(), doctorId,
                                                          existingAppointment.getPatientId(), patientId,
                                                          existingAppointment.getDate(), date,
                                                          existingAppointment.getTime(), time));
        }

        table.put(new AppointmentId(appointmentId),
                  new Appointment(appointmentId, patientId, doctorId, date, time));
    }
}
