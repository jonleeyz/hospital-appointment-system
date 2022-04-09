package model;

final class DoctorId extends Id {

    protected DoctorId(String id) {
        super(id);
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        DoctorId otherDoctorId = (DoctorId) other;
        if (otherDoctorId.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }
}
