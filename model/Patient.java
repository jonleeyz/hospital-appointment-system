package model;

import java.lang.IllegalArgumentException;
// import java.lang.UnsupportedOperationException;

class Patient {
    // can be updated if more than one gender is to be recorded
    enum Gender {
        M,
        F
    }

    private PatientId id;
    private String name;
    private int age;
    private Gender gender;

    protected Patient(String id, String name, int age, String gender) throws IllegalArgumentException {
        this.id = new PatientId(id);
        this.name = name;
        this.age = age;

        if (age < 0) {
            throw new IllegalArgumentException(String.format("Specified age [%d] cannot be negative.", age));
        }
        
        if (gender == "M") {
            this.gender = Gender.M;
        } else if (gender == "F") {
            this.gender = Gender.F;
        } else {
            throw new IllegalArgumentException(
                String.format("Missing implementation for specified gender: %s.", gender));
        }
    }

    protected PatientId getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected int getAge() {
        return age;
    }

    protected Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        if (otherPatient.getId().equals(this.id) &&
            otherPatient.getName().equals(this.name) &&
            otherPatient.getAge() == this.age &&
            otherPatient.getGender() == this.gender) {
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
        result = prime * result + age;
        result = prime * result + gender.hashCode();

        return result;
    }

    @Override
    public String toString() {
        String output = String.format("");

        return output;
    }
}
