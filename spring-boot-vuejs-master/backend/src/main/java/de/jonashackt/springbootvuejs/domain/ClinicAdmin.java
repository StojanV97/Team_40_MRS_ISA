package de.jonashackt.springbootvuejs.domain;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="clinicAdministrators")
public class ClinicAdmin extends User {



    public Long clinicID;
    public ClinicAdmin() {
        super();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Clinic clinic;

    public ClinicAdmin(String firstName, String lastName,String email, String userName, String password) {
        super(firstName,lastName,email,userName,password);
        this.clinicID = null;
    }

    public void setClinicName(Long clinicName) {
        this.clinicID = clinicName;
    }

    public Long getClinicID() {
        return clinicID;
    }

    public void setClinicID(Long clinicID) {
        this.clinicID = clinicID;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
}
