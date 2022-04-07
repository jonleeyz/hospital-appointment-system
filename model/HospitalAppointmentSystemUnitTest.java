package model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class HospitalAppointmentSystemUnitTest {
    private HospitalAppointmentSystem has1;

    @BeforeEach
    void init() {
        has1 = new HospitalAppointmentSystem();
    }

    @Disabled
    @Test
    void parseCsvToStringsWorksCorrectly() {

    }

    @Disabled
    @Test
    void verifyCsvSchemaWorksCorrectly() {
        List<String> correctSchema = Arrays.asList(HospitalAppointmentSystem.CONFIGURED_HEADER);
        has1.verifyCsvSchema(correctSchema);

        List<String> blankSchema = new ArrayList<String>();
        List<String> correctLengthButWrongSchema = new ArrayList<String>();
        for (int i = 0; i < correctSchema.size(); i++) {
            correctLengthButWrongSchema.add("doctorId");
        }
        List<String> incompleteSchema = correctSchema.subList(0, 6);

        List<List<String>> wrongSchemaList = new ArrayList<List<String>>();
        wrongSchemaList.add(blankSchema);
        wrongSchemaList.add(correctLengthButWrongSchema);
        wrongSchemaList.add(incompleteSchema);

        for (List<String> wrongSchema: wrongSchemaList) {
            Exception e = assertThrows(IllegalArgumentException.class,
                                       () -> has1.verifyCsvSchema(wrongSchema));
            assertEquals(String.join("CSV file is of incorrect format. ",
                                     "See CONFIGURED_HEADER for correct schema."),
                         e.getMessage());
        }

    }

    @Disabled
    @Test
    void populateFieldsWithStringsWorksCorrectly() {

    }
}
