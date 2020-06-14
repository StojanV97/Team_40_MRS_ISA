package de.jonashackt.springbootvuejs.domain;



import javax.persistence.*;
import java.util.ArrayList;

@SuppressWarnings("JpaAttributeTypeInspection")


@Entity
@Table(name="patients")
public class Patient extends User{

    private String country;
    private String city;
    private String address;
    private String phoneNumber;
    private String insuranceNumber;
    //public MedicalRecord;
    ArrayList<Long> appointments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "patient")
    private MedicalRecord medicalRecord;


    public Patient(String firstName, String lastName, String email, String userName, String password, String country, String city, String address, String phoneNumber, String insuranceNumber, ArrayList<Long> appointmentse) {
        super(firstName, lastName, email, userName, password);
        this.country = country;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = insuranceNumber;
        this.appointments = appointmentse;
    }

    public ArrayList<Long> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Long> appointments) {
        this.appointments = appointments;
    }

    public Patient() {
        super();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

}
