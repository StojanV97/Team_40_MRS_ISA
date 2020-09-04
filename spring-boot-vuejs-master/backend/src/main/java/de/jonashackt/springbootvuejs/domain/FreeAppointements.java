package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name = "Free_Appointements")
public class FreeAppointements{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    String dateAndTime;
    //int duration;
    String type;
    long roomID;
    long patientID;
    long doctorID;
    long clinicID;

    public FreeAppointements()
    {

    }

    @Override
    public String toString() {
        return "FreeAppointements{" +
                "id=" + id +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", type='" + type + '\'' +
                ", roomID=" + roomID +
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

    public FreeAppointements(String dateAndTime, String type, long roomID, long patientID, long doctorID,long clinicID) {
        this.dateAndTime = dateAndTime;
        this.type = type;
        this.roomID = roomID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.clinicID = clinicID;
    }
    public FreeAppointements(String dateAndTime, String type, long roomID, long doctorID,long clinicID) {
        this.dateAndTime = dateAndTime;
        this.type = type;
        this.roomID = roomID;

        this.doctorID = doctorID;
        this.clinicID = clinicID;
    }


    public long getId() {
        return id;
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

    public long getClinicID() {
        return clinicID;
    }

    public void setClinicID(long clinicID) {
        this.clinicID = clinicID;
    }

    public void setId(long id) {
        this.id = id;
    }
}

