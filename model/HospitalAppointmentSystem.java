package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.entities.Appointment;
import model.entities.id.AppointmentId;
import model.entities.id.DoctorId;
import model.entities.id.PatientId;

import java.time.LocalDate;
import java.time.LocalTime;

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
    
    public HospitalAppointmentSystem(String csvFilePath) throws FileNotFoundException {
        List<List<String>> csvData = parseCsvToStrings(csvFilePath);

        try {
            verifyCsvSchema(csvData.get(0));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("CSV file is missing its header.");
        }

        populateFieldsWithStrings(csvData.subList(1, csvData.size()));
    }

    public ArrayList<Appointment> getAppointments(String doctorIdRaw, String dateAndTime) {
        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
        String[] dateAndTimeSplit = dateAndTime.split(" ");
        String dateRaw = dateAndTimeSplit[0];

        try {
            assert doctorTable.contains(doctorIdRaw);
        } catch (AssertionError ae) {
            System.out.println(String.format("No Doctor with DoctorId %s exists. Returning empty array.",
                                             doctorIdRaw));
            return appointmentList;
        }

        DoctorId doctorId = doctorTable.get(doctorIdRaw).getId();
        LocalDate date = LocalDate.parse(dateRaw);
        HashMap<LocalTime, AppointmentId> appointmentsHashedByTime = doctorIndexedAppointmentTable.getAll(doctorId, date);

        for (AppointmentId appointmentId: appointmentsHashedByTime.values()) {
            appointmentList.add(appointmentTable.get(appointmentId.toString()));
        }
        return appointmentList;
    }

    public void fixAppointment(String patientIdRaw, String doctorIdRaw, String dateAndTime) {
        // Assume that patientId and doctorId must already exist.
        // check if patientId and doctorId exists. If either does not exist, then fail gracefully.
        try {
            assert patientTable.contains(patientIdRaw);
        } catch (AssertionError ae) {
            System.out.println(String.format(String.join(" ",
                                                         "Appointment cannot be created:",
                                                         "Patient with PatientId %s does not exist."),
                                             patientIdRaw));
            return;
        }

        try {
            assert doctorTable.contains(doctorIdRaw);
        } catch (AssertionError ae) {
            System.out.println(String.format(String.join(" ",
                                                         "Appointment cannot be created:",
                                                         "Doctor with DoctorId %s does not exist."),
                                             doctorIdRaw));
            return;
        }
        
        PatientId patientId = patientTable.get(patientIdRaw).getId();
        DoctorId doctorId = doctorTable.get(doctorIdRaw).getId();

        String[] dateAndTimeSplit = dateAndTime.split(" ");
        String dateRaw = dateAndTimeSplit[0];
        String timeRaw = dateAndTimeSplit[1];

        String appointmentIdIncomplete = String.format("A-%s-%s-", doctorId, dateRaw);
        int appointmentDayIndex = 1;
        while (appointmentTable.contains(String.join("",
                                                     appointmentIdIncomplete,
                                                     Integer.toString(appointmentDayIndex)))) {
            appointmentDayIndex++;
        }
        String appointmentIdRaw = String.join("",
                                              appointmentIdIncomplete,
                                              Integer.toString(appointmentDayIndex));

        appointmentTable.create(appointmentIdRaw, patientId, doctorId, dateRaw, timeRaw); 
        doctorIndexedAppointmentTable.add(doctorId,
                                          LocalDate.parse(dateRaw, Appointment.DATE_FORMATTER),
                                          LocalTime.parse(dateRaw, Appointment.TIME_FORMATTER),
                                          new AppointmentId(appointmentIdRaw));
        patientIndexedAppointmentTable.add(patientId,
                                           LocalDate.parse(dateRaw, Appointment.DATE_FORMATTER),
                                           LocalTime.parse(dateRaw, Appointment.TIME_FORMATTER),
                                           new AppointmentId(appointmentIdRaw));
    }

    public void cancelAppointment(String patientIdRaw, String doctorIdRaw, String dateAndTime) {
        try {
            assert patientTable.contains(patientIdRaw);
        } catch (AssertionError ae) {
            System.out.println(String.format(String.join(" ",
                                                         "Appointment cannot be cancelled:",
                                                         "Patient with PatientId %s does not exist."),
                                             patientIdRaw));
            return;
        }

        try {
            assert doctorTable.contains(doctorIdRaw);
        } catch (AssertionError ae) {
            System.out.println(String.format(String.join(" ",
                                                         "Appointment cannot be cancelled:",
                                                         "Doctor with DoctorId %s does not exist."),
                                             doctorIdRaw));
            return;
        }

        String[] dateAndTimeSplit = dateAndTime.split(" ");
        String dateRaw = dateAndTimeSplit[0];
        String timeRaw = dateAndTimeSplit[1];

        DoctorId doctorId = doctorTable.get(doctorIdRaw).getId();
        PatientId patientId = patientTable.get(patientIdRaw).getId();
        LocalDate date = LocalDate.parse(dateRaw, Appointment.DATE_FORMATTER);
        LocalTime time = LocalTime.parse(timeRaw, Appointment.TIME_FORMATTER);
        AppointmentId appointmentId;

        try {
            appointmentId = doctorIndexedAppointmentTable.getAppointmentId(doctorId, date, time);
        } catch (NoSuchElementException nsee) {
            System.out.println(String.join(" ",
                                           "Appointment cannot be cancelled.",
                                           nsee.getMessage()));
            return;
        }
        
        doctorIndexedAppointmentTable.remove(doctorId, date, time, appointmentId);
        patientIndexedAppointmentTable.remove(patientId, date, time, appointmentId);
    }

    List<List<String>> parseCsvToStrings(String csvFilePath) throws FileNotFoundException {
        List<List<String>> data = new ArrayList<List<String>>();
        File file = new File(csvFilePath);

        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            List<String> lineData = Arrays.asList(s.nextLine().split(","));
            data.add(lineData);
        }
        s.close();
        return data;
    }

    void verifyCsvSchema(List<String> header) {
        int configuredHeaderIndex = 0;
        for (String s: header) {
            String field = s.trim();
            if (!(field.equals(CONFIGURED_HEADER[configuredHeaderIndex++]))) {
                throw new IllegalArgumentException(String.join(" ",
                                                               "CSV file is of incorrect format.",
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
            String doctorIdRaw = record.get(fieldNumber++).trim();
            String doctorName = record.get(fieldNumber++).trim();

            try {
                doctorTable.create(doctorIdRaw, doctorName);
            } catch (IllegalStateException ise) {
                assert doctorTable.verifyDetails(doctorIdRaw, doctorName);
            } catch (AssertionError ae) {
                throw new IllegalArgumentException(
                        String.format(String.join(" ",
                                                  "Invalid Doctor details at record %d: Doctor with DoctorId %s",
                                                  "was previously registered with different details."),
                                      recordNumber, doctorIdRaw));
            }
            DoctorId doctorId = doctorTable.get(doctorIdRaw).getId();

            /** Parsing Patient details */
            String patientIdRaw = record.get(fieldNumber++).trim();
            String patientName = record.get(fieldNumber++).trim();
            String patientAgeRaw = record.get(fieldNumber++).trim();
            String patientGender = record.get(fieldNumber++).trim();

            int patientAge;
            try {
                patientAge = Integer.parseInt(patientAgeRaw);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                            String.format(String.join(" ",
                                                      "Invalid Patient age at record %d: specified age %s",
                                                      "cannot be parsed to int."),
                                          recordNumber, patientAgeRaw));
            }

            try {
                patientTable.create(patientIdRaw, patientName, patientAge, patientGender);
            } catch (IllegalStateException ise) {
                assert patientTable.verifyDetails(patientIdRaw, patientName, patientAge, patientGender);
            } catch (AssertionError ae) {
                throw new IllegalArgumentException(
                        String.format(String.join(" ",
                                                  "Invalid Patient details at record %d: Patient with PatientId %s",
                                                  "was previously registered with different details."),
                                      recordNumber, patientIdRaw));
            }
            PatientId patientId = patientTable.get(patientIdRaw).getId();

            /** Parsing Appointment details */
            String appointmentIdRaw = record.get(fieldNumber++).trim();
            String appointmentDateTimeRaw = record.get(fieldNumber++).trim();

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
                        String.format(String.join(" ",
                                                  "Invalid Appointment details at record %d:",
                                                  "Appointment with AppointmentId %s",
                                                  "was previously registered with different details."),
                                      recordNumber, appointmentIdRaw));
            }


            Appointment appointment = appointmentTable.get(appointmentIdRaw);
            AppointmentId appointmentId = appointment.getAppointmentId();
            LocalDate appointmentDate = appointment.getDate();
            LocalTime appointmentTime = appointment.getTime();

            doctorIndexedAppointmentTable.add(doctorId, appointmentDate, appointmentTime, appointmentId);
            patientIndexedAppointmentTable.add(patientId, appointmentDate, appointmentTime, appointmentId);
        }
    }
}
