package model.entities;

import java.lang.IllegalArgumentException;

import model.entities.id.PatientId;

public final class Patient {
    enum Gender {
        M,
        F
    }

    private PatientId id;
    private String name;
    private int age;
    private Gender gender;

    /**
     * - @throws IllegalArgumentException if specified age is negative.
     * - @throws IllegalArgumentException if specified gender is not implemented.
     */
    public Patient(String id, String name, int age, String gender) throws IllegalArgumentException {
        this.id = new PatientId(id);
        this.name = name;
        this.age = age;

        if (age < 0) {
            throw new IllegalArgumentException(String.format("Specified age [%d] cannot be negative.", age));
        }

        if (gender.equals("M")) {
            System.out.print("here");
            this.gender = Gender.M;
        } else if (gender.equals("F")) {
            this.gender = Gender.F;
        } else {
            throw new IllegalArgumentException(
                String.format("Missing implementation for specified gender: %s.", gender));
        }
    }

    public PatientId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
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
    // TODO: implememt
    public String toString() {
        String output = String.format("");

        return output;
    }
}
