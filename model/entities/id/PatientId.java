package model.entities.id;

public final class PatientId extends Id {
    public PatientId(String id) {
        super(id);
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
}
