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

    public void setListOfPatients(ArrayList<Long> listOfPatients) {
        this.listOfPatients = listOfPatients;
    }

    public void setListOfAppoitnements(ArrayList<String> listOfAppoitnements) {
        this.listOfAppoitnements = listOfAppoitnements;
    }

    public ArrayList<String> getListOfTerms() {
        return listOfTerms;
    }

    public void setListOfTerms(ArrayList<String> listOfTerms) {
        this.listOfTerms = listOfTerms;
    }

    ArrayList<String> listOfAppoitnements;
    ArrayList<String> listOfTerms;



    public void setListOfAppoitnements(String listOfAppoitnements) {
        this.listOfAppoitnements.add(listOfAppoitnements);
    }

    public ArrayList<String> getListOfAppoitnements() {
        return listOfAppoitnements;
    }

    public Doctor(long ID,String firstName, String lastName, String email, String userName, String password) {
        super(ID,firstName, lastName, email, userName, password);
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
