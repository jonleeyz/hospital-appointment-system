package model.tables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.NoSuchElementException;

import model.entities.id.AppointmentId;

public class IndexedAppointmentTable<T> {
    private HashMap<T, HashMap<LocalDate, HashMap<LocalTime, AppointmentId>>> table
        = new HashMap<T, HashMap<LocalDate, HashMap<LocalTime, AppointmentId>>>();

    public boolean isEmpty() {
        return table.isEmpty();
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

    public void remove(T id, LocalDate date, LocalTime time, AppointmentId appointmentId) throws NoSuchElementException {
        if (table.get(id).get(date).get(time).equals(appointmentId)) {
            table.get(id).get(date).remove(time);
        } else {
            throw new NoSuchElementException(String.format("No matching Appointment for AppointmentId %s.",
                                             appointmentId));
        }
    }

    public boolean contains(T id, LocalDate date, LocalTime time) {
        return table.get(id).get(date).get(time) != null;
    }

    public AppointmentId getAppointmentId(T id, LocalDate date, LocalTime time) throws NoSuchElementException {
        if (contains(id, date, time)) {
            return table.get(id).get(date).get(time);
        } else {
            throw new NoSuchElementException(String.format(String.join(" ",
                                                                       "No matching Appointment for given parameters:",
                                                                       "id %s, date %s, time %s."),
                                                           id,
                                                           date.toString(),
                                                           time.toString()));
        }
    }
}