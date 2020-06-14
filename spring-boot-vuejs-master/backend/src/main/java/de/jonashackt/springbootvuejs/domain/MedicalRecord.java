package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {
    @Id
    private Long medicalRecordId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private int height;
    private int weight;
    private String bloodType;
    private String allergies;

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medicalrecord")
    //private Set<AppointmentReport> appointmentReports;

    public MedicalRecord() {
    }

    public Long getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(Long medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    /*public Set<AppointmentReport> getAppointmentReports() {
        return appointmentReports;
    }

    public void setAppointmentReports(Set<AppointmentReport> appointmentReports) {
        this.appointmentReports = appointmentReports;
    }*/
}
