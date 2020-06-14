package de.jonashackt.springbootvuejs.domain;

import java.util.ArrayList;

public class CustomMessage {
    public String message;
    public ArrayList<String> email;

    public CustomMessage(){

    }
    public CustomMessage(String message, String email) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEmail(String email) {
        this.email.add(email);
    }
}
