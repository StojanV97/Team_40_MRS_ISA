package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.function.LongSupplier;

@SuppressWarnings("ALL")
@Entity
@Table(name="doctors")
public class Doctor extends User {

    ArrayList<Long> listOfPatients;
    public Doctor() {
        super();
    }
    ArrayList<String> listOfAppoitnements;



    public void setListOfAppoitnements(String listOfAppoitnements) {
        this.listOfAppoitnements.add(listOfAppoitnements);
    }

    public ArrayList<String> getListOfAppoitnements() {
        return listOfAppoitnements;
    }

    public Doctor(String firstName, String lastName, String email, String userName, String password) {
        super(firstName, lastName, email, userName, password);
        this.listOfPatients = new ArrayList<Long>();
        this.listOfAppoitnements = new ArrayList<String>();
    }

    public ArrayList<Long> getListOfPatients() {
        return listOfPatients;
    }

    public void setListOfPatients(Long listOfPatients) {
        this.listOfPatients .add(listOfPatients);
    }
}
