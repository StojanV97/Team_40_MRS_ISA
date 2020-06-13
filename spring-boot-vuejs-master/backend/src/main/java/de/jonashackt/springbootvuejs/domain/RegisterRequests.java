package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name="registerRequests")
public class RegisterRequests {

    public static String REQUEST_TABLE = "";
    // PrimaryKey
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String country;
    private String city;
    private String address;
    private String phoneNumber;
    private String insuranceNumber;

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RegisterRequests(){};

    public RegisterRequests( String firstName, String lastName, String email, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
    }

    public RegisterRequests(String firstName, String lastName, String email, String userName, String country, String city, String address, String phoneNumber, String insuranceNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.insuranceNumber = insuranceNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }
}


