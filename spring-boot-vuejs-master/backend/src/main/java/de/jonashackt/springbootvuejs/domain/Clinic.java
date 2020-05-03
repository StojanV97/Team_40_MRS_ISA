package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clinics")
public class Clinic {
    @Id
    private long id;
    private String name;
    private String address;
    private String administrator; // ovo treba biti admin klinike, kad se napravi

    public Clinic(){}

    public Clinic(long id, String name, String address, String administrator) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.administrator = administrator;
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

    public String getAdministrator() {
        return administrator;
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

    public void setAdministrator(String administrator) {
        this.administrator = administrator;
    }
}
