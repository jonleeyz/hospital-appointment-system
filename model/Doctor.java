package model;

class Doctor {
    private DoctorId id;
    private String name;

    Doctor(String id, String name) {
        this.id = new DoctorId(id);
        this.name = name;
    }

    DoctorId getId() {
        return id;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Doctor otherDoctor = (Doctor) other;
        if (otherDoctor.getId().equals(this.id) &&
            otherDoctor.getName().equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        result = prime * result + id.hashCode();
        result = prime * result + name.hashCode();

        return result;
    }

    @Override
    public String toString() {
        String output = String.format("");

        return output;
    }
}

class DoctorId {
    String id;

    DoctorId(String id) {
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