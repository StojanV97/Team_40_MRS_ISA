package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments_requests")
public class AppointmentRequest {

    public static String USER_TABLE = "";
    // PrimaryKey


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String dateAndTime;
    //int duration;
    AppointmentType type;

    long patientID;
    long doctorID;
    long clinicID;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClinicID() {
        return clinicID;
    }

    public void setClinicID(long clinicID) {
        this.clinicID = clinicID;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(long doctorID) {
        this.doctorID = doctorID;
    }

    public AppointmentRequest()
    {

    }
    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public AppointmentRequest(String dateAndTime, AppointmentType type, long clinicID, long patientID, long doctorID) {
        this.dateAndTime = dateAndTime;
        this.type = type;
        this.clinicID = clinicID;
        this.patientID = patientID;
        this.doctorID = doctorID;
    }


}
