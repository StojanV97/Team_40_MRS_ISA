package de.jonashackt.springbootvuejs.joinedtables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Clinic_Doctors")
public class Clinic_Doctors {


    private Long Clinic_ID;
    @Id
    @Column(updatable = false)
    private Long Doctor_ID;

    public Clinic_Doctors(){

    }

    public Clinic_Doctors(Long cID, Long dID){
        this.Clinic_ID = cID;
        this.Doctor_ID = dID;
    }

    public Long getClinic_ID() {
        return Clinic_ID;
    }

    public Long getDoctor_ID() {
        return Doctor_ID;
    }

    public void setClinic_ID(Long clinic_ID) {
        Clinic_ID = clinic_ID;
    }

    public void setDoctor_ID(Long doctor_ID) {
        Doctor_ID = doctor_ID;
    }
}
