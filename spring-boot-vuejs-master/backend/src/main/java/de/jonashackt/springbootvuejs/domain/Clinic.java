package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("ALL")
@Entity
@Table(name = "clinics")
public class Clinic
{
    @Id
    private long id;
    private String name;
    private String address;

   // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private Set<ClinicAdmin> administrator = new HashSet<ClinicAdmin>();
    private String administrator;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "Clinic_Doctors", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"))
    private Set<Doctor> doctors = new HashSet<Doctor>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "CLINIC_ROOMS", joinColumns = @JoinColumn(name = "clinic_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "roomID"))
    private Set<Room> rooms = new HashSet<Room>();


    public Clinic() {
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public Clinic(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Clinic(long id, String name, String address, String city) {
        this.id = id;
        this.name = name;
        this.address = address;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public String getAdministrator() {
        return administrator;
    }

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
}
