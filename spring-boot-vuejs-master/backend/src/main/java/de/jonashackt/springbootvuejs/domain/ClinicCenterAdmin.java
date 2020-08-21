package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="clinicCenterAdministrators")
public class ClinicCenterAdmin extends User{

    public ClinicCenterAdmin() {
        super();
    }

    public ClinicCenterAdmin(long ID,String firstName, String lastName, String email, String userName, String password) {
        super(ID,firstName, lastName, email, userName, password);
    }
}
