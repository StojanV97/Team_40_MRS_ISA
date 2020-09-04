package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.*;
import de.jonashackt.springbootvuejs.domain.AppointmentRequest;
import de.jonashackt.springbootvuejs.domain.Clinic;
import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.service.AppointmentRequestService;
import de.jonashackt.springbootvuejs.service.ClinicService;
import de.jonashackt.springbootvuejs.service.RequestService;
import de.jonashackt.springbootvuejs.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController()
@RequestMapping("/api")
public class RequestController {


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoomService roomService;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    private AppointmentRequestRepository appointmentRequestRepository;
    @Autowired
    private RequestService requestService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    ApointmentRepository apointmentRepository;
    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    private RequestRepository requestRepository;


    @Autowired
    private DaysOffRequestRepository daysOffRequestRepository;
    @Autowired
    private DaysOffRepository daysOffRepository;

    @PostMapping(value = "admin/delete-days-request/{id}")
    public ResponseEntity<?> deleteDaysoffReq(@PathVariable long id){
        daysOffRequestRepository.deleteById(id);
        Collection<DaysOffRequest> col = (Collection<DaysOffRequest>) daysOffRequestRepository.findAll();
        return new ResponseEntity<Collection<DaysOffRequest>>(col,HttpStatus.OK);
    }
    @PostMapping(value = "admin/create-days-off/")
    public ResponseEntity<?> createDaysOff(@RequestBody DaysOffRequest daysOffRequest){
        DaysOff daysOff = new DaysOff(daysOffRequest.getType(),daysOffRequest.getUserID(),daysOffRequest.getDateFrom(),daysOffRequest.getDateTo());
        daysOffRepository.save(daysOff);
        return new ResponseEntity<String>("ok",HttpStatus.OK);
    }


    @GetMapping(value = "admin/get-days-off-requests/{doctorID}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<DaysOffRequest>> getDaysOffRequests(@PathVariable long doctorID) {
        Collection<Clinic> clinics = clinicService.getAllClinics();
        long doctorsClinic = 999999;
        for(Clinic c : clinics){
            for(Doctor d : c.getDoctors()){
                if(d.getId() == doctorID){
                    doctorsClinic = c.getId();
                    break;
                }
            }
        }
        Collection<DaysOffRequest> daysOffRequests  = (Collection<DaysOffRequest>) daysOffRequestRepository.findAll();
        ArrayList<DaysOffRequest> listOfRequests = new ArrayList<DaysOffRequest>();
        for(DaysOffRequest dr : daysOffRequests){
            if(dr.getClinicID() == doctorsClinic){
                listOfRequests.add(dr);
            }
        }



        return new ResponseEntity<Collection<DaysOffRequest>>(listOfRequests, HttpStatus.OK);
    }

    @PostMapping(value = "/user/create-days-off",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRequest (@RequestBody DaysOffRequest daysOffRequest){
        long userID = daysOffRequest.getUserID();
        Collection<Clinic> listOfClinics  = clinicService.getAllClinics() ;
        long clinicID = 9999999;
        for(Clinic c : listOfClinics){
            for(Doctor d : c.getDoctors()){
                if(userID == d.getId()){
                    clinicID = c.getId();
                    break;
                }
            }
        }
        daysOffRequest.setClinicID(clinicID);
        daysOffRequestRepository.save(daysOffRequest);
        return  new ResponseEntity<String>("OK",HttpStatus.OK);

    }


    @PostMapping(value = "/auth/registration",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRequest(@RequestBody RegisterRequests request) throws Exception {
        String s = requestService.createRequest(request);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }


    @GetMapping(value = "requests/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegisterRequests>> getGreetings() {
        Collection<RegisterRequests> requests = requestService.getAllRequests();
        return new ResponseEntity<Collection<RegisterRequests>>(requests, HttpStatus.OK);
    }


    @PostMapping(value = "request/delete/{userName}")
    private ResponseEntity<String> deleteRoom(@PathVariable String userName) {
        String s = "s";
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "admin/get-appointment-requests/{clinicID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAppointmentRequests(@PathVariable Long clinicID) {
        Collection<AppointmentRequest> listOfRequests = appointmentRequestService.getAllRequests();
        ArrayList<AppointmentRequest> requestsForClinic = new ArrayList<AppointmentRequest>();
        for (AppointmentRequest ap : listOfRequests) {
            if (ap.getClinicID() == clinicID) {
                requestsForClinic.add(ap);
            }
        }

        return new ResponseEntity<ArrayList<AppointmentRequest>>(requestsForClinic, HttpStatus.OK);
    }

    @PostMapping(value = "admin/delete-appoitnment-request/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id) {
        appointmentRequestService.delete(id);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


    @PostMapping(value = "admin/automatic-appointment/{clinicID}/{date}")
    public ResponseEntity<?> automaticAppointment(@PathVariable Long clinicID, @PathVariable String date, @RequestBody AppointmentRequest appointmentRequest) throws ParseException {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date now = new Date();
        Date startDate =  sp.parse(appointmentRequest.getDateCreated());
        Date endDate = sp.parse(appointmentRequest.getDeletionDate());
        if(now.after(endDate)){
            Collection<Room> listas = roomService.gettAllRooms();
            for(Room r : listas){
                System.out.println(r.getCalendar());
            }
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
            final long ONE_MINUTE_IN_MILLIS = 60000;
            Clinic c = clinicService.getClinic(clinicID);
            for (Room r : c.getRooms()) {
                if (!r.getCalendar().isEmpty()) {
                    ArrayList<Date> listOfDates = new ArrayList<Date>();
                    for (String s : r.getCalendar()) {
                        Date d = sp.parse(s);
                        Date d2 = new Date(d.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                        listOfDates.add(d);
                    }
                    Collections.sort(listOfDates);
                    Date current = new Date();
                    String currentDate = sp.format(current);
                    String[] currentDateSplit = currentDate.split(" ");
                    String firstTerm = currentDateSplit[0] + " 08-00";
                    Date firstTermDate = sp.parse(firstTerm);

                    Date firstDate = listOfDates.get(0);
                    Date lastDate = listOfDates.get(listOfDates.size() - 1);
                    boolean noFree = true;
                    while (firstTermDate.before(lastDate)) {
                        String dateFirst = sp.format(firstTermDate);
                        String[] split = dateFirst.split(" ");

                        if (termini.contains(split[1])) {
                            if (listOfDates.contains(firstTermDate)) {
                            } else {
                                r.setFirstAvailableDate(sp.format(firstTermDate));
                                noFree = false;
                                break;
                            }
                        }
                        firstTermDate = new Date(firstTermDate.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                    }
                    if (noFree) {
                        lastDate = new Date(lastDate.getTime() + (1440 * ONE_MINUTE_IN_MILLIS));
                        String last = sp.format(lastDate);
                        String[] pslit = last.split(" ");
                        String newLastDate = pslit[0] + " 10-00";
                        r.setFirstAvailableDate(newLastDate);
                    }
                }
            }
            Collection<Room> listaSobaa =  c.getRooms();
            ArrayList<Room> listaSoba = new ArrayList<>(listaSobaa);
            for (Room r : listaSoba) {
                if (r.getCalendar().isEmpty()) {
                    r.setFirstAvailableDate(appointmentRequest.getDateAndTime() + " 10-00");
                }
            }
            ArrayList<Room> freeRoomsForDate = new ArrayList<>();
            for (Room r : listaSoba) {
                if (r.getCalendar().isEmpty()) {
                    freeRoomsForDate.add(r);
                } else {
                    ArrayList<String> busy = new ArrayList<String>();
                    boolean flag = false;
                    for (String s : r.getCalendar()) {
                        String[] splited = s.split(" ");
                        if (splited[0].equalsIgnoreCase(appointmentRequest.getDateAndTime())) {
                            flag = true;
                            busy.add(splited[1]);
                        }
                    }

                    if (busy.size() != termini.size()) {
                        freeRoomsForDate.add(r);
                    } else {
                        System.out.println("Isti");
                    }
                }
            }
            Appointment appointment = new Appointment();
            appointment.setDoctorID(appointmentRequest.getDoctorID());
            appointment.setPatientID(appointmentRequest.getPatientID());
            appointment.setClinicID(clinicID);
            appointment.setType("EXAMINATION");
            Random random = new Random();
            if (freeRoomsForDate.size() == 0) {
                int randomInteger = random.nextInt(listaSoba.size());
                ArrayList<String> appt = new ArrayList<>(listaSoba.get(0).getCalendar());
                appt.add(listaSoba.get(0).getFirstAvailableDate());
                listaSoba.get(0).setCalendar(appt);
                roomRepository.save(listaSoba.get(0));
                Optional<User> d = userRepository.findById(appointment.getDoctorID());
                Doctor doctor = (Doctor) d.get();
                doctor.setListOfPatients(appointment.getPatientID());
                doctor.setListOfAppoitnements(listaSoba.get(0).getFirstAvailableDate());
                userRepository.save(doctor);
                appointment.setRoomID(listaSoba.get(0).getRoomID());
                appointment.setDateAndTime(listaSoba.get(0).getFirstAvailableDate());
                apointmentRepository.save(appointment);
                return new ResponseEntity<Collection<Room>>(listaSoba, HttpStatus.OK);
            }

            int randomInteger = random.nextInt(freeRoomsForDate.size());
            Room newroom = roomService.getRoom(freeRoomsForDate.get(0).getRoomID());
            String []dejt = newroom.getFirstAvailableDate().split(" ");
            appointment.setRoomID(newroom.getRoomID());
            appointment.setDateAndTime(newroom.getFirstAvailableDate());
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        }

        return new ResponseEntity<String>("Empty", HttpStatus.OK);



    }

    @PostMapping(value = "admin/automatic-appointment-operation/{clinicID}/{date}")
    public ResponseEntity<?> automaticAppointmentOperation(@PathVariable Long clinicID, @PathVariable String date, @RequestBody AppointmentRequest appointmentRequest) throws ParseException {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date now = new Date();
        Date startDate =  sp.parse(appointmentRequest.getDateCreated());
        Date endDate = sp.parse(appointmentRequest.getDeletionDate());
        if(now.after(endDate)){
            System.out.println("Datum sa fronta: " +date);
            Collection<Room> listas = roomService.gettAllRooms();
            for(Room r : listas){
                System.out.println(r.getCalendar());
            }
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
            final long ONE_MINUTE_IN_MILLIS = 60000;
            Clinic c = clinicService.getClinic(clinicID);
            for(Room room : c.getRooms()){
                System.out.println(room.getCalendar());
            }
            for (Room r : c.getRooms()) {
                if (!r.getCalendar().isEmpty()) {
                    ArrayList<Date> listOfDates = new ArrayList<Date>();
                    for (String s : r.getCalendar()) {
                        Date d = sp.parse(s);
                        Date d2 = new Date(d.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                        listOfDates.add(d);
                    }
                    Collections.sort(listOfDates);
                    Date current = new Date();
                    String currentDate = sp.format(current);
                    String[] currentDateSplit = currentDate.split(" ");
                    String firstTerm = currentDateSplit[0] + " 08-00";
                    Date firstTermDate = sp.parse(firstTerm);

                    Date firstDate = listOfDates.get(0);
                    Date lastDate = listOfDates.get(listOfDates.size() - 1);
                    boolean noFree = true;
                    while (firstTermDate.before(lastDate)) {
                        String dateFirst = sp.format(firstTermDate);
                        String[] split = dateFirst.split(" ");

                        if (termini.contains(split[1])) {
                            if (listOfDates.contains(firstTermDate)) {
                            } else {
                                r.setFirstAvailableDate(sp.format(firstTermDate));
                                noFree = false;
                                break;
                            }
                        }
                        firstTermDate = new Date(firstTermDate.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                    }
                    if (noFree) {
                        lastDate = new Date(lastDate.getTime() + (1440 * ONE_MINUTE_IN_MILLIS));
                        String last = sp.format(lastDate);
                        String[] pslit = last.split(" ");
                        String newLastDate = pslit[0] + " 10-00";
                        r.setFirstAvailableDate(newLastDate);
                    }
                }
            }
            Collection<Room> listaSobaa =  c.getRooms();
            ArrayList<Room> listaSoba = new ArrayList<>(listaSobaa);
            for (Room r : listaSoba) {
                if (r.getCalendar().isEmpty()) {
                    r.setFirstAvailableDate(appointmentRequest.getDateAndTime() + " 10-00");
                }
            }
            ArrayList<Room> freeRoomsForDate = new ArrayList<>();
            for (Room r : listaSoba) {
                if (r.getCalendar().isEmpty()) {
                    freeRoomsForDate.add(r);
                } else {
                    ArrayList<String> busy = new ArrayList<String>();
                    boolean flag = false;
                    for (String s : r.getCalendar()) {
                        String[] splited = s.split(" ");
                        if (splited[0].equalsIgnoreCase(appointmentRequest.getDateAndTime())) {
                            flag = true;
                            busy.add(splited[1]);
                        }
                    }

                    if (busy.size() != termini.size()) {
                        freeRoomsForDate.add(r);
                    } else {
                        System.out.println("Isti");
                    }
                }
            }
            Appointment appointment = new Appointment();
            appointment.setDoctorID(appointmentRequest.getDoctorID());
            appointment.setPatientID(appointmentRequest.getPatientID());
            appointment.setType("OPERATION");
            Random random = new Random();
            if (freeRoomsForDate.size() == 0) {
                int randomInteger = random.nextInt(listaSoba.size());
                ArrayList<String> appt = new ArrayList<>(listaSoba.get(0).getCalendar());
                appt.add(listaSoba.get(0).getFirstAvailableDate());
                listaSoba.get(0).setCalendar(appt);
                roomRepository.save(listaSoba.get(0));
                Optional<User> d = userRepository.findById(appointment.getDoctorID());
                Doctor doctor = (Doctor) d.get();
                doctor.setListOfPatients(appointment.getPatientID());
                doctor.setListOfAppoitnements(listaSoba.get(0).getFirstAvailableDate());
                userRepository.save(doctor);
                appointment.setRoomID(listaSoba.get(0).getRoomID());
                appointment.setDateAndTime(listaSoba.get(0).getFirstAvailableDate());
                apointmentRepository.save(appointment);
                return new ResponseEntity<Collection<Room>>(listaSoba, HttpStatus.OK);
            }

            int randomInteger = random.nextInt(freeRoomsForDate.size());
            Room newroom = roomService.getRoom(freeRoomsForDate.get(0).getRoomID());
            String []dejt = newroom.getFirstAvailableDate().split(" ");
            appointment.setRoomID(newroom.getRoomID());
            appointment.setDateAndTime(newroom.getFirstAvailableDate());
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        }

        return new ResponseEntity<String>("Empty", HttpStatus.OK);



    }

    @GetMapping(value = "admin/get-first-dates/{clinicID}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirstDates(@PathVariable Long clinicID, @PathVariable String date) throws ParseException {
        System.out.println("Datum: " + date);
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
        final long ONE_MINUTE_IN_MILLIS = 60000;
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        sp.setTimeZone(TimeZone.getTimeZone("GMT"));
        Clinic c = clinicService.getClinic(clinicID);

        for (Room r : c.getRooms()) {
            if (!r.getCalendar().isEmpty()) {
                ArrayList<Date> listOfDates = new ArrayList<Date>();
                for (String s : r.getCalendar()) {
                    Date d = sp.parse(s);
                    Date d2 = new Date(d.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                    listOfDates.add(d);
                }
                Collections.sort(listOfDates);
                Date current = new Date();
                String currentDate = sp.format(current);
                String[] currentDateSplit = currentDate.split(" ");
                String firstTerm = currentDateSplit[0] + " 08-00";
                Date firstTermDate = sp.parse(firstTerm);

                Date firstDate = listOfDates.get(0);
                Date lastDate = listOfDates.get(listOfDates.size() - 1);
                boolean noFree = true;
                while (firstTermDate.before(lastDate)) {
                    String dateFirst = sp.format(firstTermDate);
                    String[] split = dateFirst.split(" ");

                    if (termini.contains(split[1])) {
                        if (listOfDates.contains(firstTermDate)) {
                        } else {
                            r.setFirstAvailableDate(sp.format(firstTermDate));
                            noFree = false;
                            break;
                        }
                    }
                    firstTermDate = new Date(firstTermDate.getTime() + (30 * ONE_MINUTE_IN_MILLIS));
                }
                if (noFree) {
                    lastDate = new Date(lastDate.getTime() + (1440 * ONE_MINUTE_IN_MILLIS));
                    String last = sp.format(lastDate);
                    String[] pslit = last.split(" ");
                    String newLastDate = pslit[0] + " 10-00";
                    r.setFirstAvailableDate(newLastDate);
                }
            }
        }
        Collection<Room> listaSoba = c.getRooms();
        for (Room r : listaSoba) {
            if (r.getCalendar().isEmpty()) {
                r.setFirstAvailableDate(date + " 10-00");
            }
        }
        ArrayList<Room> freeRoomsForDate = new ArrayList<>();
        for (Room r : listaSoba) {
            if (r.getCalendar().isEmpty()) {
                freeRoomsForDate.add(r);
            } else {
                ArrayList<String> busy = new ArrayList<String>();
                boolean flag = false;
                for (String s : r.getCalendar()) {
                    String[] splited = s.split(" ");
                    if (splited[0].equalsIgnoreCase(date)) {
                        flag = true;
                        busy.add(splited[1]);
                    }
                }

                if (busy.size() != termini.size()) {
                    freeRoomsForDate.add(r);
                } else {
                    System.out.println("Isti");
                }
            }
        }

        if (freeRoomsForDate.size() == 0) {
            return new ResponseEntity<Collection<Room>>(listaSoba, HttpStatus.OK);
        }
        return new ResponseEntity<Collection<Room>>(freeRoomsForDate, HttpStatus.OK);

    }

    @PostMapping(value = "patient/create-appointment-request/{dateAndTime}/{type}/{patientID}/{doctorID}/{clinicID}")
    public ResponseEntity<String> createAppointmentRequest(@PathVariable String dateAndTime,@PathVariable String type, @PathVariable long clinicID, @PathVariable long patientID, @PathVariable long doctorID ){
        AppointmentRequest ar = new AppointmentRequest(dateAndTime,type,clinicID,patientID,doctorID);
        appointmentRequestRepository.save(ar);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}