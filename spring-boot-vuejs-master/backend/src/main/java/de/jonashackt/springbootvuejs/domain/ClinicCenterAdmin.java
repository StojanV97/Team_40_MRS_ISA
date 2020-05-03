package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="clinicCenterAdministrators")
public class ClinicCenterAdmin extends User{

    public ClinicCenterAdmin() {
        super();
    }

    public ClinicCenterAdmin(String firstName, String lastName, String email, String userName, String password) {
        super(firstName, lastName, email, userName, password);
    }
}
