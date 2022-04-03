package model;

import java.lang.IllegalArgumentException;
// import java.lang.UnsupportedOperationException;

class Patient {
    // can be updated if more than one gender is to be recorded
    enum Gender {
        M,
        F
    }

    private String id;
    private String name;
    private int age;
    private Gender gender;

    Patient(String id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        
        if (gender == "M") {
            this.gender = Gender.M;
        } else if (gender == "F") {
            this.gender = Gender.F;
        } else {
            throw new IllegalArgumentException(
                String.format("No gender implementation for specified gender: %s.", gender));
        }
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }

    Gender getGender() {
        return gender;

        /*
        if (gender == Gender.M) {
            return "M";
        } else if (gender == Gender.F) {
            return "F";
        } else {
            throw new UnsupportedOperationException(
                String.format("Missing implementation for Patient.getGender for specified gender: %s.",
                              gender.toString()));
        }
        */
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