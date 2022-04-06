package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AppointmentUnitTest {
    private String appointmentId1Raw;
    private DoctorId doctor1Id;
    private PatientId patient1Id;
    private String date1Raw;
    private String time1Raw;
    private Appointment appointment1;
    
    @BeforeEach
    @Disabled
    void init() {
        appointmentId1Raw = "A5";
        doctor1Id = new DoctorId("A1");
        patient1Id = new PatientId("B2");
        date1Raw = "";
        time1Raw = "";

        appointment1 = new Appointment(patient1Id, doctor1Id, date1Raw, time1Raw);
    }

    @Test
    @Disabled
    void constructorTest() {
    }

    @Test
    @Disabled
    void equalsOverrideTest() {
    }
}
