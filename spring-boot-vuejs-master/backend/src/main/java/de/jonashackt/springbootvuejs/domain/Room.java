package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "room")
public class Room {

    @Id
    private long roomID;
    private String roomName;
    @SuppressWarnings("JpaAttributeTypeInspection")
    private ArrayList<String> calendar;

    public Room() {
    }


    public Room(long id, String roomType) {
        this.roomID = id;
        this.roomName = roomType;
        this.calendar = new ArrayList<String>();
    }


    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                ", calendar=" + calendar +
                '}';
    }

    public long getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public ArrayList<String> getCalendar() {
        return calendar;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCalendar(ArrayList<String> calendar) {
        this.calendar = calendar;
    }
}
