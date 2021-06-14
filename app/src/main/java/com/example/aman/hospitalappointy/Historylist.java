package com.example.aman.hospitalappointy;
import com.google.firebase.database.Query;

public class Historylist {

    private String Doctor_name;
    private String Sick;
    private String Prescription;
    private String Date;
    private String Sympon;
    private String PatientName;

    public Historylist() {
    }

    public Historylist(String doctor_name, String sick, String prescription, String date, String sympon, String patientName) {
        Doctor_name = doctor_name;
        Sick = sick;
        Prescription = prescription;
        Date = date;
        Sympon = sympon;
        PatientName = patientName;
    }

    public String getDoctor_name() {
        return Doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        Doctor_name = doctor_name;
    }

    public String getSick() {
        return Sick;
    }

    public void setSick(String sick) {
        Sick = sick;
    }

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSympon() {
        return Sympon;
    }

    public void setSympon(String sympon) {
        Sympon = sympon;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }
}
