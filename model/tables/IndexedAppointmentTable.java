package model.tables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.NoSuchElementException;

import model.entities.id.Id;

import model.entities.id.AppointmentId;
import util.DateTimeParser;

public class IndexedAppointmentTable<T extends Id> {
    private HashMap<T, HashMap<LocalDate, HashMap<LocalTime, AppointmentId>>> table
        = new HashMap<T, HashMap<LocalDate, HashMap<LocalTime, AppointmentId>>>();

    public boolean isEmpty() {
        return table.isEmpty();
    }

    /**
     * Returns true if there exists an AppointmentId in the table that matches the provided arguments
     * and false otherwise.
     */
    public boolean containsAppointmentId(T id, LocalDate date, LocalTime time) {
        try {
            return table.get(id).get(date).get(time) != null;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    /**
     * Returns the AppointmentId corresponding to the provided arguments.
     * @throws NoSuchElementException if no stored AppointmentId matches the provided arguments.
     */
    public AppointmentId getAppointmentId(T id, LocalDate date, LocalTime time) throws NoSuchElementException {
        if (containsAppointmentId(id, date, time)) {
            return table.get(id).get(date).get(time);
        } else {
            throw new NoSuchElementException(String.format(String.join(" ",
                                                                       "No matching Appointment for given parameters:",
                                                                       "id %s, date %s, time %s."),
                                                           id,
                                                           DateTimeParser.toString(date),
                                                           DateTimeParser.toString(time)));
        }
    }

    public HashMap<LocalTime, AppointmentId> getAll(T id, LocalDate date) {
        return table.get(id).get(date);
    }

    public void add(T id, LocalDate date, LocalTime time, AppointmentId appointmentId) {
        if (table.get(id) == null) {
            table.put(id, new HashMap<LocalDate, HashMap<LocalTime, AppointmentId>>());
        }

        if (table.get(id).get(date) == null) {
            table.get(id).put(date, new HashMap<LocalTime, AppointmentId>());
        }
        table.get(id).get(date).put(time, appointmentId);
    }

    /**
     * Removes the AppointmentId corresponding to the provided arguments.
     * @throws NoSuchElementException if no stored AppointmentId matches the provided arguments.
     */
    public void remove(T id, LocalDate date, LocalTime time, AppointmentId appointmentId) throws NoSuchElementException {
        try {
            if (getAppointmentId(id, date, time).equals(appointmentId)) {
                table.get(id).get(date).remove(time);
                return;
            } 
        } catch (NoSuchElementException nsee) {
        }
        throw new NoSuchElementException(String.format(String.join(" ",
                                                                   "No matching Appointment for given parameters:",
                                                                   "id %s, date %s, time %s, AppointmentId %s"),
                                                       id, DateTimeParser.toString(date),
                                                       DateTimeParser.toString(time), appointmentId));
    }
}
