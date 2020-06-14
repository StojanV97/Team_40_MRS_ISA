package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name = "DAYS_OFF_REQUEST")
public class DaysOffRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userID;
    private String firstName;
    private String lastname;
    private String dateFrom;
    private String dateTo;
    private String type;

    public DaysOffRequest(){

    }

    public DaysOffRequest(Long userID, String firstName, String lastname, String dateFrom, String dateTo,String type) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastname = lastname;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DaysOffRequest{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
