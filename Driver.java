import model.HospitalAppointmentSystem;

import java.io.FileNotFoundException;

public class Driver {
    static HospitalAppointmentSystem singletonSystem = null;
    public static void main(String[] args) {
        try {
            HospitalAppointmentSystem demoSystem = new HospitalAppointmentSystem("../test/data/dummy.csv");
        } catch (FileNotFoundException fne) {
            System.out.println(String.format("Terminating parse: CSV file not found at %s",
                                           fne.getMessage()));
        }
        return;
    }
}
