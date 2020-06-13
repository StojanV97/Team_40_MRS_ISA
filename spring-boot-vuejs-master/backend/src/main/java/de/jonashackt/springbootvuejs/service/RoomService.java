package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public String addRoom(Room room){
        if(roomRepository.findByRoomID(room.getRoomID()) == null ){
        roomRepository.save(room);
        return "800";
        }
        return "801";

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
        roomRepository.save(room);
        return "code";
    }

    public Room getRoom(Long id){
        Optional<Room> room = roomRepository.findById(id);
        Room r = null;
        if(room.isPresent()){
            r = room.get();
        }
        return r;
    }
}
