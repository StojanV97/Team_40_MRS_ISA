package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.joinedtables.ClinicRooms;
import de.jonashackt.springbootvuejs.repository.ClinicRoomRepository;
import de.jonashackt.springbootvuejs.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/api")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    ClinicRoomRepository clinicRoomRepository;

    @PostMapping(value = "room/registration/{clinicID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> registerRoom(@RequestBody Room room, @PathVariable long clinicID) {
        String s = roomService.addRoom(room);
        if (s == "800") {
            ClinicRooms cr = new ClinicRooms(clinicID, room.getRoomID());
            clinicRoomRepository.save(cr);
            return new ResponseEntity<String>(s, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(s, HttpStatus.OK);
        }
    }

    @PostMapping(value = "room/delete/{roomID}")
    private ResponseEntity<String> deleteRoom(@PathVariable long roomID) {
        clinicRoomRepository.deleteById(roomID);
        String s = roomService.delete(roomID);
        return new ResponseEntity<String>("sad", HttpStatus.OK);
    }

    @PostMapping(value = "room/edit/{clinicID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> editRoom(@RequestBody Room room, @PathVariable long clinicID) {
        String s = roomService.editRoomData(room);
        ClinicRooms cr = new ClinicRooms(clinicID, room.getRoomID());
        clinicRoomRepository.save(cr);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }


    @GetMapping(value = "room/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Room>> getGreetings() {
        Collection<Room> greetings = roomService.gettAllRooms();
        return new ResponseEntity<Collection<Room>>(greetings, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/free-terms/{id}/{date}")
    public ResponseEntity<ArrayList<String>> getFreeTerms(@PathVariable long id,@PathVariable String date) {
        ArrayList<String> termini = new ArrayList<String>();
        termini.add("10-00");
        termini.add("10-30");
        termini.add("11-00");
        termini.add("11-30");
        termini.add("12-00");
        termini.add("12-30");
        termini.add("13-00");
        termini.add("13-30");
        termini.add("14-00");
        Room r = roomService.getRoom(id);

        if(r.getCalendar().isEmpty()){
            return new ResponseEntity<ArrayList<String>>(termini, HttpStatus.OK);

        }

        for(String tDate : r.getCalendar()){
                if(tDate.contains(date)){
                    String[] splited = tDate.split(" ");
                    termini.remove(splited[1]);
                    System.out.println(splited[1]);
                }
        }
        System.out.println(termini);

        if(r.getCalendar().contains(date)){
            System.out.println(r.getCalendar());
            String[] dateArray = date.split(" ");
            ArrayList<String> terms = new ArrayList<String>();
            for (String term : r.getCalendar()){

                String[] termSpliter = term.split(" ");
                if(!termini.contains(termSpliter[1])){
                    terms.add(termSpliter[1]);
                }

            }
            return new ResponseEntity<ArrayList<String>>(r.getCalendar(), HttpStatus.OK);

        }

        return new ResponseEntity<ArrayList<String>>(termini, HttpStatus.OK);
    }
}
