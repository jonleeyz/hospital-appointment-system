package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

class IndexedAppointmentTable<T> {
    private HashMap<T, HashMap<LocalDate, HashSet<AppointmentId>>> table
        = new HashMap<T, HashMap<LocalDate, HashSet<AppointmentId>>>();

    boolean isEmpty() {
        return table.isEmpty();
    }

    HashSet<AppointmentId> getAll(T id, LocalDate date) {
        return table.get(id).get(date);
    }

    void add(T id, LocalDate date, AppointmentId appointmentId) {
        table.get(id).get(date).add(appointmentId);
    }

    void remove(T id, LocalDate date, AppointmentId appointmentId) {
        table.get(id).get(date).remove(appointmentId);
    }
}
