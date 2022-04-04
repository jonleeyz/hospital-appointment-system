package model;

final class PatientId {
    protected String id;

    protected PatientId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        PatientId otherPatientId = (PatientId) other;
        if (otherPatientId.id.equals(this.id)) {
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