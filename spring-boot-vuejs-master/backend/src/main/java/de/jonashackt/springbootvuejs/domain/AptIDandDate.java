package de.jonashackt.springbootvuejs.domain;

public class AptIDandDate {

    public String date;
    public Long id;

    public AptIDandDate(){

    }
    public AptIDandDate(String date, Long id) {
        this.date = date;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
