package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Appointment {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    private AppointmentId appointmentId;
    private PatientId patientId;
    private DoctorId doctorId;
    private LocalDate date;
    private LocalTime time;
    private int duration;

    protected Appointment(AppointmentId appointmentId, PatientId patientId, DoctorId doctorId,
                          String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = LocalDate.parse(date, DATE_FORMATTER);
        this.time = LocalTime.parse(time, TIME_FORMATTER);
        this.duration = 60;
    }

    protected AppointmentId getAppointmentId() {
        return appointmentId;
    }

    protected PatientId getPatientId() {
        return patientId;
    }

    protected DoctorId getDoctorId() {
        return doctorId;
    }

    protected LocalDate getDate() {
        return date;
    }

    protected LocalTime getTime() {
        return time;
    }

    protected int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        if (otherAppointment.getAppointmentId().equals(this.getAppointmentId()) &&
            otherAppointment.getPatientId().equals(this.getPatientId()) &&
            otherAppointment.getDoctorId().equals(this.getDoctorId()) &&
            otherAppointment.getDate().equals(this.getDate()) &&
            otherAppointment.getTime().equals(this.getTime()) &&
            otherAppointment.getDuration() == this.getDuration()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
    
        result = prime * result + patientId.hashCode();
        result = prime * result + doctorId.hashCode();
        result = prime * result + date.hashCode();
        result = prime * result + time.hashCode();
        result = prime * result + duration;

        return result;
    }

    @Override
    public String toString() {
        String output = String.format("");

        return output;
    }
}
