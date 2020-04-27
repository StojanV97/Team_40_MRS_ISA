package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="doctors")
public class Doctor extends User {

    public Doctor() {
        super();
    }

    public Doctor(String firstName, String lastName, String email, String userName, String password) {
        super(firstName, lastName, email, userName, password);
    }

}
