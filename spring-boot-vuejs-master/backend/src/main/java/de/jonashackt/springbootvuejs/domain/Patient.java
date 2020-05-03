package de.jonashackt.springbootvuejs.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="patients")
public class Patient extends User{

    //public MedicalRecord;
    public Patient(String firstName, String lastName,String email, String userName, String password) {
        super(firstName,lastName,email,userName,password);
    }


    public Patient() {
        super();
    }

}
