package model;

final class AppointmentId extends Id {

    protected AppointmentId(String id) {
        super(id);
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
}
