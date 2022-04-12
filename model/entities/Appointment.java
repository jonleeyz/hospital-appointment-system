package model.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;

import model.entities.id.AppointmentId;
import model.entities.id.DoctorId;
import model.entities.id.PatientId;

import util.DateTimeParser;

public final class Appointment {
    private static final Integer[] monthsWithout31stArray = {4, 6, 9, 11};
    private static final HashSet<Integer> monthsWithout31st = new HashSet<Integer>(Arrays.asList(monthsWithout31stArray));
    
    private AppointmentId appointmentId;
    private PatientId patientId;
    private DoctorId doctorId;
    private LocalDate date;
    private LocalTime time;
    private int duration;

    /**
     * - @throws IllegalArgumentException if a created Date object specifies day 31 with a month that does not have
     *   the 31st.
     * - @throws IllegalArgumentException if a Created Date object specifies day 29 or above with month 2; adjusted
     *   for leap years.
     */
    public Appointment(String appointmentId, String patientId, String doctorId, String date, String time)
        throws IllegalArgumentException {
        this.appointmentId = new AppointmentId(appointmentId);
        this.patientId = new PatientId(patientId);
        this.doctorId = new DoctorId(doctorId);
        this.date = DateTimeParser.parseToDate(date);
        this.time = DateTimeParser.parseToTime(time);
        this.duration = 60;
        
        // not working
        // handles invalid Dates: months that have no 31st
        if ((monthsWithout31st.contains(this.date.getMonthValue()) && this.date.getDayOfMonth() == 31)) {
            throw new IllegalArgumentException(String.format(String.join(" ",
                                                                         "Parsed date is invalid: Invalid value for",
                                                                         "DayOfMonth when MonthOfYear is %d: %d"),
                                                             this.date.getMonthValue(), this.date.getDayOfMonth()));
        }
        // not working
        // handles invalid Dates: February, for leap and normal years
        if ((this.date.getMonthValue() == 2) &&
            ((this.date.getYear() % 4 == 0 && this.date.getDayOfMonth() > 29) ||
             (this.date.getYear() % 4 == 1 && this.date.getDayOfMonth() > 28))) {
            throw new IllegalArgumentException(String.format(String.join(" ",
                                                                         "Parsed date is invalid: Invalid value for",
                                                                         "DayOfMonth when MonthOfYear is %d: %d"),
                                                             this.date.getMonthValue(), this.date.getDayOfMonth()));
                
        }
    }

    public AppointmentId getAppointmentId() {
        return appointmentId;
    }

    public PatientId getPatientId() {
        return patientId;
    }

    public DoctorId getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDuration() {
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
    // TODO: implememt
    public String toString() {
        String output = String.format("");

        return output;
    }
}
