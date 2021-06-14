package com.example.aman.hospitalappointy;

import com.google.firebase.database.Query;



public class BookedAppointmentList {

    private String Date;
    private String Time;
    private String Doctor_ID;
    private String PatientID;
    private String Sympon;



    public BookedAppointmentList() {
    }

    public BookedAppointmentList(String date, String time, String doctor_ID, String patientID) {
        this.Date = date;
        this.Time = time;
        this.Doctor_ID = doctor_ID;
        this.PatientID = patientID;
    }
    public String getSympon() {
        return Sympon;
    }

    public void setSympon(String sympon) {
        Sympon = sympon;
    }

    public BookedAppointmentList(String sympon) {
        Sympon = sympon;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getDoctor_ID() {
        return Doctor_ID;
    }

    public void setDoctor_ID(String doctor_ID) {
        this.Doctor_ID = doctor_ID;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        this.PatientID = patientID;
    }
}

