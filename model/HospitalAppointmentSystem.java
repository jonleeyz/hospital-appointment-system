package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class HospitalAppointmentSystem {
    private static final String[] CONFIGURED_HEADER = {"doctor_id", "doctor_name",
                                                       "patient_id", "patient_name", "patient_age", "patient_gender",
                                                       "appointment_id", "appointment_datetime"};

    private DoctorTable doctorTable = new DoctorTable();
    private PatientTable patientTable = new PatientTable();
    private HashMap<AppointmentId, Appointment> appointmentTable = new HashMap<AppointmentId, Appointment>();
    
    private HashMap<DoctorId, AppointmentId> doctorIndexedAppointmentTable = new HashMap<DoctorId, AppointmentId>();
    private HashMap<PatientId, AppointmentId> patientIndexedAppointmentTable = new HashMap<PatientId, AppointmentId>();
   
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
        if (patientTable.containsKey(new PatientId(patientId)) &&
            doctorTable.containsKey(new DoctorId(doctorId))) {
            // parse string into date and time
            // create new Appointment object and assign id
            // add Appointment object to tables
        }

        return;
    }

    public void cancelAppointment(String patientId, String doctorId, String dateAndTime) {
        return;
    }

    private List<List<String>> parseCsvToStrings(String csvFilePath) {
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

    private void verifyCsvSchema(List<String> header) {
        int configuredHeaderIndex = 0;
        for (String s: header) {
            String field = s.trim();
            if (!(field.equals(CONFIGURED_HEADER[configuredHeaderIndex++]))) {
                throw new IllegalArgumentException();
            }
        }
    }
