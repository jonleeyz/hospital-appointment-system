package model;

class Doctor {
    private DoctorId id;
    private String name;

    protected Doctor(String id, String name) {
        this.id = new DoctorId(id);
        this.name = name;
    }

    protected DoctorId getId() {
        return id;
    }

    protected String getName() {
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
