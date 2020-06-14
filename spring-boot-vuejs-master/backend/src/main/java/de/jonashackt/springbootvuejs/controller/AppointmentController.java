package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.repository.AppointmentRepository;
import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.ApointmentRepository;
import de.jonashackt.springbootvuejs.repository.RoomRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.service.RoomService;

import de.jonashackt.springbootvuejs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

    @Autowired
    ApointmentRepository apointmentRepository;

    @Autowired
    RoomService roomService;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;
    @PostMapping(value = "admin/create-appoitnment/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAppoitment(@RequestBody Appointment appointment){
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
        String newTerm = "";
        Room room = roomService.getRoom(appointment.getRoomID());
       if(room.getCalendar().isEmpty()){
           newTerm = appointment.getDateAndTime() + " " + "10-00";
           appointment.setDateAndTime(appointment.getDateAndTime() + " 10-00");
       }else{
           ArrayList<String> busy = new ArrayList<String>();
           for(String s : room.getCalendar()){
               String[] splited = s.split(" ");
               if(splited[0].equalsIgnoreCase(appointment.getDateAndTime())){
                   busy.add(splited[1]);
               }
           }
           if(busy.isEmpty()){
               newTerm = appointment.getDateAndTime() + " " + "10-00";
               appointment.setDateAndTime(appointment.getDateAndTime() + " " + "10-00");
           }else{
                boolean flag = true;
                busyFor : for(String s : termini) {
                    if(busy.contains(s)){
                        System.out.println();
                    }else{
                        newTerm = appointment.getDateAndTime() + " " + s;
                        appointment.setDateAndTime(appointment.getDateAndTime() + " " + s);
                        break busyFor;
                    }
                }
           }
       }
        System.out.println(room.getCalendar());
        ArrayList<String> appt = new ArrayList<>(room.getCalendar());
        appt.add(newTerm);
        room.setCalendar(appt);
        roomRepository.save(room);
        Room newRoom  =  roomService.getRoom(room.getRoomID());
        System.out.println(newRoom.getCalendar());

      Optional<User> d = userRepository.findById(appointment.getDoctorID());
      Doctor doctor = (Doctor) d.get();
      doctor.setListOfPatients(appointment.getPatientID());
      doctor.setListOfAppoitnements(newTerm);
      userRepository.save(doctor);
      apointmentRepository.save(appointment);
      AptIDandDate aptd = new AptIDandDate(newTerm,appointment.getId());
      //Treba dodati u listu kod pacijenta datum

        return new ResponseEntity<AptIDandDate>(aptd, HttpStatus.OK);
    }



}
