package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class HospitalAppointmentSystem {
    public static final String[] CONFIGURED_HEADER = {"doctor_id", "doctor_name",
                                                      "patient_id", "patient_name", "patient_age", "patient_gender",
                                                      "appointment_id", "appointment_datetime"};

    private DoctorTable doctorTable = new DoctorTable();
    private PatientTable patientTable = new PatientTable();
    private AppointmentTable appointmentTable = new AppointmentTable();

    private IndexedAppointmentTable<DoctorId> doctorIndexedAppointmentTable = new IndexedAppointmentTable<DoctorId>();
    private IndexedAppointmentTable<PatientId> patientIndexedAppointmentTable = new IndexedAppointmentTable<PatientId>();

    public HospitalAppointmentSystem() {
    }
    
    public HospitalAppointmentSystem(String csvFilePath) {
        List<List<String>> csvData = null;

        try {
            csvData = parseCsvToStrings(csvFilePath);
        } catch (Exception e) {
            //TODO: handle exception
        }

        try {
            verifyCsvSchema(csvData.get(0));
        } catch (Exception e) {
            //TODO: handle exception
        }

        populateFieldsWithStrings(csvData.subList(1, csvData.size()));
    }

    public Appointment[] getAppointments(String doctorID, String dateAndTime) {
        // to be implemented
        return new Appointment[0];
    }

    public void fixAppointment(String patientId, String doctorId, String dateAndTime) {
        // Assume that patientId and doctorId must already exist.
        // check if patientId and doctorId exists. If either does not exist, then fail gracefully.
        if (patientTable.contains(patientId) &&
            doctorTable.contains(doctorId)) {
            // parse string into date and time
            // create new Appointment object and assign id
            // add Appointment object to tables
        }

        return;
    }

    public void cancelAppointment(String patientId, String doctorId, String dateAndTime) {
        return;
    }

    List<List<String>> parseCsvToStrings(String csvFilePath) {
        List<List<String>> data = new ArrayList<List<String>>();
        File file = new File(csvFilePath);

        try {
            Scanner s = new Scanner(file);
            while (s.hasNextLine()) {
                List<String> lineData = Arrays.asList(s.nextLine().split(","));
                data.add(lineData);
            }
            s.close();
        } catch (FileNotFoundException e) {
            //TODO: handle
        }
        return data;
    }

    void verifyCsvSchema(List<String> header) {
        int configuredHeaderIndex = 0;
        for (String s: header) {
            String field = s.trim();
            if (!(field.equals(CONFIGURED_HEADER[configuredHeaderIndex++]))) {
                throw new IllegalArgumentException(String.join("CSV file is of incorrect format. ",
                                                               "See CONFIGURED_HEADER for correct schema."));
            }
        }
    }
    
    /**
     * Populates the tables in the HospitalAppointmentSystem with csv data.
     * - @throws IllegalArgumentException if a record has Doctor information that does not match with a previously
     *   registered Doctor with the same DoctorId.
     * - @throws IllegalArgumentException if a record has Patient age information that cannot be parsed to an int.
     * - @throws IllegalArgumentException if a record has Patient information that does not match with a previously
     *   registered Patient with the same PatientId.
     * - @throws IllegalArgumentException if a record has invalid Patient information (eg. age, gender). 
     */
    void populateFieldsWithStrings(List<List<String>> csvData) throws IllegalArgumentException {
        int recordNumber = 1;

        for (List<String> record: csvData) {
            int fieldNumber = 0;

            /** Parsing Doctor details */
            String doctorIdRaw = record.get(fieldNumber++);
            String doctorName = record.get(fieldNumber++);

            try {
                doctorTable.create(doctorIdRaw, doctorName);
            } catch (IllegalStateException ise) {
                assert doctorTable.verifyDetails(doctorIdRaw, doctorName);
            } catch (AssertionError ae) {
                throw new IllegalArgumentException(
                        String.format(String.join("Invalid Doctor details at record %d: Doctor with DoctorId %s ",
                                                  "was previously registered with different details."),
                                      recordNumber, doctorIdRaw));
            }
            DoctorId doctorId = doctorTable.get(doctorIdRaw).getId();

            /** Parsing Patient details */
            String patientIdRaw = record.get(fieldNumber++);
            String patientName = record.get(fieldNumber++);
            String patientAgeRaw = record.get(fieldNumber++);
            String patientGender = record.get(fieldNumber++);

            int patientAge;
            try {
                patientAge = Integer.parseInt(patientAgeRaw);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                            String.format(String.join("Invalid Patient age at record %d: specified age %s ",
                                                      "cannot be parsed to int."),
                                          recordNumber, patientAgeRaw));
            }

            try {
                patientTable.create(patientIdRaw, patientName, patientAge, patientGender);
            } catch (IllegalStateException ise) {
                assert patientTable.verifyDetails(patientIdRaw, patientName, patientAge, patientGender);
            } catch (AssertionError ae) {
                throw new IllegalArgumentException(
                        String.format(String.join("Invalid Patient details at record %d: Patient with PatientId %s ",
                                                  "was previously registered with different details."),
                                      recordNumber, patientIdRaw));
            }
            PatientId patientId = patientTable.get(patientIdRaw).getId();

            /** Parsing Appointment details */
            String appointmentIdRaw = record.get(fieldNumber++);
            String appointmentDateTimeRaw = record.get(fieldNumber++);
            String[] appointmentDateTimeSplit = appointmentDateTimeRaw.split(" ");
            String appointmentDateRaw = appointmentDateTimeSplit[0];
            String appointmentTimeRaw = appointmentDateTimeSplit[1];

            try {
                appointmentTable.create(appointmentIdRaw, patientId, doctorId, appointmentDateRaw, appointmentTimeRaw);
            } catch (IllegalStateException ise) {
                assert appointmentTable.verifyDetails(appointmentIdRaw, patientId, doctorId,
                                                      appointmentDateRaw, appointmentTimeRaw);
            } catch (AssertionError ae) {
                throw new IllegalArgumentException(
                        String.format(String.join("Invalid Appointment details at record %d: ",
                                                  "Appointment with AppointmentId %s ",
                                                  "was previously registered with different details."),
                                      recordNumber, appointmentIdRaw));
            }


            Appointment appointment = appointmentTable.get(appointmentIdRaw);
            AppointmentId appointmentId = appointment.getAppointmentId();
            LocalDate appointmentDate = appointment.getDate();

            doctorIndexedAppointmentTable.add(doctorId, appointmentDate, appointmentId);
            patientIndexedAppointmentTable.add(patientId, appointmentDate, appointmentId);
        }
    }
}
