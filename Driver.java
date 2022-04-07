import model.HospitalAppointmentSystem;

import java.io.FileNotFoundException;

public class Driver {
    static HospitalAppointmentSystem singletonSystem = null;
    public static void main(String[] args) {
        try {
            HospitalAppointmentSystem demoSystem = new HospitalAppointmentSystem(args[0]);
        } catch (FileNotFoundException fne) {
            System.out.println(String.format("Terminating parse: CSV file not found at %s",
                                           fne.getMessage()));
        }
        return;
    }
}
