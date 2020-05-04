package de.jonashackt.springbootvuejs.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="clinicAdministrators")
public class ClinicAdmin extends User {



    public ClinicAdmin() {
        super();
    }

    public ClinicAdmin(String firstName, String lastName,String email, String userName, String password) {
        super(firstName,lastName,email,userName,password);
    }

}
