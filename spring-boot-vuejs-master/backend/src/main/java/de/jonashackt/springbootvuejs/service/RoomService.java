package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public String addRoom(Room room){
        try{
            System.out.println(room);
        }catch (Exception e){
            System.out.println("Greska");
        }
        roomRepository.save(room);
        return "800";

    }

    public Collection<Room> gettAllRooms(){
        return (Collection<Room>) roomRepository.findAll();
    }
    public String delete(long id){
        if(roomRepository.findByRoomID(id) != null ){
            Room r = roomRepository.findByRoomID(id);
            roomRepository.delete(r);
            return "700";
        }else{
            return "707";
        }


    }
    public String editRoomData(Room room){
        String code = "";
        return code;
    }
}
