package model;

final class DoctorId {
    protected String id;

    protected DoctorId(String id) {
        this.id = id;
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
