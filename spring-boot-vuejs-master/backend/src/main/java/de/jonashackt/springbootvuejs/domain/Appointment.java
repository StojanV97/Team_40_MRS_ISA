package de.jonashackt.springbootvuejs.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointment {

    public static String USER_TABLE = "";
    // PrimaryKey


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String dateAndTime;
    //int duration;
    AppointmentType type;

    long roomID;
    long patientID;
    long doctorID;
    long clinicID;

    public long getClinicID() {
        return clinicID;
    }

    public void setClinicID(long clinicID) {
        this.clinicID = clinicID;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
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

    public Appointment()
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

    public Appointment(String dateAndTime, AppointmentType type, long roomID, long patientID,  long doctorID,long clinicID) {
        this.dateAndTime = dateAndTime;
        this.type = type;
        this.roomID = roomID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.clinicID = clinicID;
    }
}
