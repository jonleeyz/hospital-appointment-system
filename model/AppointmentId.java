package model;

final class AppointmentId {
    protected String id;

    protected AppointmentId(String id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        AppointmentId otherAppointmentId = (AppointmentId) other;
        if (otherAppointmentId.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        String output = String.format("PatientID obj: %s", id);
        return output;
    }
}