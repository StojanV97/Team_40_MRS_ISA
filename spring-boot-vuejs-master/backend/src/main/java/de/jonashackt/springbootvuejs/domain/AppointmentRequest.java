package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
    String type;

    long patientID;
    long doctorID;
    long clinicID;
    String dateCreated;
    String deletionDate;
    @Transient
    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");

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

    @Override
    public String toString() {
        return "AppointmentRequest{" +
                "id=" + id +
                ", dateAndTime=" + dateAndTime +
                ", type=" + type +
                ", patientID=" + patientID +
                ", doctorID=" + doctorID +
                ", clinicID=" + clinicID +
                '}';
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(String deletionDate) {
        this.deletionDate = deletionDate;
    }

    public AppointmentRequest(String dateAndTime, String type, long clinicID, long patientID, long doctorID) {
        this.dateAndTime = dateAndTime;
        this.type = type;
        this.clinicID = clinicID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        Date d = new Date();
        Date e = new Date(d.getTime() + 1440 * 60000);
       // Date e = new Date();
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.dateCreated = sp.format(d);
        this.deletionDate = sp.format(e);

    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
