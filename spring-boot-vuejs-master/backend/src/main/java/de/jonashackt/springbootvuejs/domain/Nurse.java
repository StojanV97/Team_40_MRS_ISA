package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="nurses")
public class Nurse extends  User {

    public Nurse() {
        super();
    }

    public Nurse(String firstName, String lastName, String email, String userName, String password) {
        super(firstName, lastName, email, userName, password);
    }
}
