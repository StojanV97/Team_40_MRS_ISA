package de.jonashackt.springbootvuejs.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="clinicAdministrators")
public class ClinicAdmin extends User {



    public Long clinicID;
    public ClinicAdmin() {
        super();
    }

    public ClinicAdmin(long ID,String firstName, String lastName,String email, String userName, String password) {
        super(ID,firstName,lastName,email,userName,password);
        this.clinicID = null;
    }

    public void setClinicName(Long clinicName) {
        this.clinicID = clinicName;
    }

}
