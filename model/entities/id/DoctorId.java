package model.entities.id;

public final class DoctorId extends Id {
    public DoctorId(String id) {
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
