package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/api")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping(value = "room/registration",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> registerRoom(@RequestBody Room room){
        String s = roomService.addRoom(room);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "room/delete/{roomID}")
    private ResponseEntity<String> deleteRoom(@PathVariable long roomID){
        String s = roomService.delete(roomID);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }
    @GetMapping(value = "room/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Room>> getGreetings() {
        Collection<Room> greetings = roomService.gettAllRooms();
        return new ResponseEntity<Collection<Room>>(greetings, HttpStatus.OK);
    }

}
