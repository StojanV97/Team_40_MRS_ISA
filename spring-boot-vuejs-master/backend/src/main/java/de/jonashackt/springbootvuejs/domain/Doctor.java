package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.function.LongSupplier;

@SuppressWarnings("ALL")
@Entity
@Table(name="doctors")
public class Doctor extends User {


    ArrayList<String> listOfPatients;
    public Doctor() {
        super();
    }

    public Doctor(String firstName, String lastName, String email, String userName, String password) {
        super(firstName, lastName, email, userName, password);
        this.listOfPatients = new ArrayList<String>();
    }

    public ArrayList<String> getListOfPatients() {
        return listOfPatients;
    }
}
