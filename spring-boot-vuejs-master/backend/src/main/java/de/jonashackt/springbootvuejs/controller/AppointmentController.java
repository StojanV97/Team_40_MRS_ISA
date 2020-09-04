package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Appointment;
import de.jonashackt.springbootvuejs.repository.*;
import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    FreeAppointementsRepository freeAppointementsRepository;


    @GetMapping(value = "user/get-current-examinations/{userID}")
    public ResponseEntity<?> getExaminations(@PathVariable Long userID) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        Collection<Appointment> listOfAppointments = (Collection<Appointment>) apointmentRepository.findAll();
        ArrayList<Appointment> doctorsListOfAppointments = new ArrayList<Appointment>();
        ArrayList<Appointment> currentAppointments = new ArrayList<Appointment>();
        for(Appointment appointment : listOfAppointments){
            if(appointment.getDoctorID() == userID){
                doctorsListOfAppointments.add(appointment);
            }
        }
        if(!doctorsListOfAppointments.isEmpty()){
            for(Appointment appointment : doctorsListOfAppointments){
                Date examinationTerm = sp.parse(appointment.getDateAndTime());
                examinationTerm.setTime(examinationTerm.getTime() - (120  * 60000));
                Date endTerm = new Date(examinationTerm.getTime() + (30  * 60000));
                if(now.after(examinationTerm) && now.before(endTerm)){
                   currentAppointments.add(appointment);
                }
            }
        }
        return new ResponseEntity<ArrayList<Appointment>>(currentAppointments,HttpStatus.ACCEPTED);
    }




    @PostMapping(value = "appointment/cancel/{id}")
    public ResponseEntity<Boolean> cancelAppointment(@PathVariable long id) throws ParseException {
        Date now = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));

        Optional<Appointment> ap = apointmentRepository.findById(id);
        Appointment appointment = new Appointment();
        if(ap.isPresent())
        {
            appointment = ap.get();
        }

        Patient patient = new Patient();
        Optional<User> p = userRepository.findById(appointment.getPatientID());
        if(p.isPresent())
        {
             patient = (Patient) p.get();
        }

        boolean cancelable = true;
        Date appointmentDate = sp.parse(appointment.getDateAndTime());
        Date current = new Date(System.currentTimeMillis());
        Date minus24 = new Date(appointmentDate.getTime() - 26*3600 * 1000);

        if(current.after(minus24))
        {

            cancelable = false;

        }else{
            apointmentRepository.delete(appointment);
            patient.getAppointments().remove(id);
            userRepository.save(patient);

        }
        return new ResponseEntity<>(cancelable,HttpStatus.ACCEPTED);
    }
    @PostMapping(value = "admin/predefined-appointements/{date}/{type}/{clinicID}/{patientID}/{doctorID}/{roomID}")
    public ResponseEntity<?> createPredefinedAppointment(@PathVariable String date,@PathVariable String type, @PathVariable Long clinicID,@PathVariable Long patientID,@PathVariable Long doctorID,@PathVariable Long roomID){
        FreeAppointements fr = new FreeAppointements(date,type,roomID,patientID,doctorID,clinicID);
        Collection<Appointment> appointements = (Collection<Appointment>) apointmentRepository.findAll();
        Collection<FreeAppointements> fappointements = (Collection<FreeAppointements>) freeAppointementsRepository.findAll();
        for(Appointment a : appointements){
            if (a.getDateAndTime().equalsIgnoreCase(fr.getDateAndTime())){
                return new ResponseEntity<String>("!OK", HttpStatus.OK);
            }
        }
        for(FreeAppointements fa : fappointements){
            if (fa.getDateAndTime().equalsIgnoreCase(fr.getDateAndTime()) && roomID == fa.getRoomID()){
                return new ResponseEntity<String>("!OK", HttpStatus.OK);
            }
        }

        freeAppointementsRepository.save(fr);
        return new ResponseEntity<String>("OK", HttpStatus.OK);

    }
    @GetMapping(value = "/admin/get-all-free-appoint/")
    public ResponseEntity<?> getAllPredef(){
        Collection<FreeAppointements> apt = (Collection<FreeAppointements>) freeAppointementsRepository.findAll();
        System.out.println(apt);
        return new ResponseEntity<Collection<FreeAppointements>>(apt,HttpStatus.OK);

    }
    @GetMapping(value = "doctor/get-apt/{id}")
    public ResponseEntity<?> getForDoctor(@PathVariable Long id){
        Collection<Appointment> apt = (Collection<Appointment>) apointmentRepository.findAll();
        for(Appointment ap : apt){
            if(!(ap.getDoctorID() == id)){
                apt.remove(ap);
            }
        }
        System.out.println(apt);
        return new ResponseEntity<Collection<Appointment>>(apt,HttpStatus.OK);

    }



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
        ArrayList<String> appt = new ArrayList<>(room.getCalendar());
        appt.add(newTerm);

        room.setCalendar(appt);
        roomRepository.save(room);
        Room newRoom  =  roomService.getRoom(room.getRoomID());


      Optional<User> d = userRepository.findById(appointment.getDoctorID());
      Doctor doctor = (Doctor) d.get();
      doctor.setListOfPatients(appointment.getPatientID());
      doctor.setListOfAppoitnements(newTerm);
      appointment.setClinicID(appointment.getClinicID());
      userRepository.save(doctor);
      apointmentRepository.save(appointment);
      AptIDandDate aptd = new AptIDandDate(newTerm,appointment.getId());
      //Treba dodati u listu kod pacijenta datum

        return new ResponseEntity<AptIDandDate>(aptd, HttpStatus.OK);
    }

    @GetMapping(value = "admin/get-income/{dates}")
    public ResponseEntity<?> getIncome(@PathVariable ArrayList<String> dates) throws ParseException {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        ArrayList<Appointment> apt = new ArrayList<Appointment>();
        Collection<Appointment> appointments = (Collection<Appointment>) apointmentRepository.findAll();
        if(dates.size() == 1){
            date1 = sp.parse(dates.get(0));
            for(Appointment a : appointments){
                String[] podeli = a.getDateAndTime().split(" ");
                Date dateOfAppointement = sp.parse(podeli[0]);
                if(dates.get(0).equalsIgnoreCase(podeli[0])){
                    apt.add(a);
                }
            }
            return new ResponseEntity<ArrayList<Appointment>>(apt,HttpStatus.OK);


        }else if (dates.size() == 0){
            return new ResponseEntity<ArrayList<Appointment>>(apt,HttpStatus.OK);
        }else{
            date1 = sp.parse(dates.get(0));
            date2 = sp.parse(dates.get(1));

        }
        if(date1.before(date2)){
            for(Appointment a : appointments){
                String[] podeli = a.getDateAndTime().split(" ");
                Date dateOfAppointement = sp.parse(podeli[0]);
                if(dateOfAppointement.after(date1) && dateOfAppointement.before(date2)){
                    apt.add(a);
                }
            }
        }else{
            for(Appointment a : appointments){
                String[] podeli = a.getDateAndTime().split(" ");
                Date dateOfAppointement = sp.parse(podeli[0]);
                if(dateOfAppointement.after(date2) && dateOfAppointement.before(date1)){
                    apt.add(a);
                }
            }
        }
        return new ResponseEntity<ArrayList<Appointment>>(apt,HttpStatus.OK);
    }

    @GetMapping(value = "admin/get-chart-apt/{type}")
    public ResponseEntity<?> getChartData(@PathVariable Long type){
        System.out.println("USO U METODU");
        Date d = new Date();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        String date = sp.format(d);
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        hmap.put("10-00",0);
        hmap.put("10-30",0);
        hmap.put("11-00",0);
        hmap.put("11-30",0);
        hmap.put("12-00",0);
        hmap.put("12-30",0);
        hmap.put("13-00",0);
        hmap.put("13-30",0);
        hmap.put("14-00",0);
        Collection<Appointment> appointments = (Collection<Appointment>) apointmentRepository.findAll();
        if(type == 1){
                ArrayList<String> dates = new ArrayList<String>();
                for(Appointment a : appointments){
                    String[] splited = a.getDateAndTime().split(" ");
                    if(splited[0].equalsIgnoreCase(date)){
                        hmap.put(splited[1],1);
                    }
                }
                return new ResponseEntity<HashMap<String,Integer>>(hmap,HttpStatus.OK);
        }else if(type == 2){

            System.out.println("USAO");
            HashMap<String, Integer> hmapWeek = new HashMap<String, Integer>();
            Date today = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(today);
            cal.add(Calendar.DAY_OF_MONTH, -7);
            Date today7 = cal.getTime();
            while(today7.before(today)){
                String datum = sp.format(today7);
                hmapWeek.put(datum,0);
                Calendar cal2 = new GregorianCalendar();
                cal2.setTime(today7);
                cal2.add(Calendar.DAY_OF_MONTH,1);
                today7 = cal2.getTime();
            }
            for(Appointment a : appointments){
                String[] split = a.getDateAndTime().split(" ");
                if(hmapWeek.containsKey(split[0])){
                    hmapWeek.put(split[0],hmapWeek.get(split[0]) + 1);
                }
            }
            ArrayList<String> arl = new ArrayList<>();
            ArrayList<Integer> aint = new ArrayList<>();
            HashMap<ArrayList<String>,ArrayList<Integer>> hashmap = new HashMap<>();
            TreeMap<String, Integer> sorted = new TreeMap<>();
            sorted.putAll(hmapWeek);
            for (Map.Entry<String, Integer> entry : sorted.entrySet())
            {
                arl.add(entry.getKey());
                aint.add(entry.getValue());
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            }
            System.out.println("IZASAO");

            hashmap.put(arl,aint);
            return new ResponseEntity<HashMap<ArrayList<String>,ArrayList<Integer>>>(hashmap,HttpStatus.OK);

        }else{
            HashMap<String, Integer> hmapMonth = new HashMap<String, Integer>();
            Date today = new Date();
            Calendar cal = new GregorianCalendar();
            cal.setTime(today);
            cal.add(Calendar.DAY_OF_MONTH, -30);
            Date today30 = cal.getTime();
            while(today30.before(today)){
                String datum = sp.format(today30);
                hmapMonth.put(datum,0);
                Calendar cal2 = new GregorianCalendar();
                cal2.setTime(today30);
                cal2.add(Calendar.DAY_OF_MONTH,1);
                today30 = cal2.getTime();
            }

            for(Appointment a : appointments){
                String[] split = a.getDateAndTime().split(" ");
                if(hmapMonth.containsKey(split[0])){
                    hmapMonth.put(split[0],hmapMonth.get(split[0]) + 1);
                }
            }
            ArrayList<String> arl2 = new ArrayList<>();
            ArrayList<Integer> aint2 = new ArrayList<>();
            HashMap<ArrayList<String>,ArrayList<Integer>> hashmap2 = new HashMap<>();
            TreeMap<String, Integer> sorted = new TreeMap<>();
            sorted.putAll(hmapMonth);
            for (Map.Entry<String, Integer> entry : sorted.entrySet())
            {
                arl2.add(entry.getKey());
                aint2.add(entry.getValue());
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());
            }


            hashmap2.put(arl2,aint2);
            return new ResponseEntity<HashMap<ArrayList<String>,ArrayList<Integer>>>(hashmap2,HttpStatus.OK);

        }
    }

    }
