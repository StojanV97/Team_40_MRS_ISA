package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.joinedtables.Clinic_Doctors;
import de.jonashackt.springbootvuejs.repository.ClinicDoctorRepository;
import de.jonashackt.springbootvuejs.repository.RequestRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.service.ClinicService;
import de.jonashackt.springbootvuejs.service.RequestService;
import de.jonashackt.springbootvuejs.service.UserService;
import de.jonashackt.springbootvuejs.service.impl.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

@RestController()
@RequestMapping("/api")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static Properties properties = new Properties();
    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ClinicDoctorRepository clinicDoctorRepository;

    @Autowired
    private RequestService  requestService;

    Random r = new Random();

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @GetMapping(value = "/account-verify/{username}")
    public ResponseEntity<String> verifyAccount(@PathVariable String username) throws MessagingException {

        System.out.println("tu smoooo");
        System.out.println(username);
        RegisterRequests patient =  requestRepository.findByUserName(username);

        userRepository.save(new Patient(patient.getFirstName(),patient.getLastName(),patient.getEmail(),patient.getUserName(),"123123",patient.getCountry(),patient.getCity(),patient.getAddress(),patient.getPhoneNumber(),patient.getInsuranceNumber()));
        requestService.delete(username);

        return new ResponseEntity<String>("Verified", HttpStatus.OK);
    }

        @PostMapping(value = "/email/{message}/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String message,@PathVariable ArrayList<String> email) throws MessagingException {



        final String username = "team40mrsisa@gmail.com";
        final String password = "no0013144";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        try {
            for(String e : email){
                msg.setFrom(new InternetAddress("team40mrsisa@gmail.com"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(e));
                msg.setSubject("Activation Code");
                Multipart emailContent = new MimeMultipart();
                MimeBodyPart textBodyPart = new MimeBodyPart();
                textBodyPart.setText(message);
                emailContent.addBodyPart(textBodyPart);
                //Attach multipart to message
                msg.setContent(emailContent);
                Transport.send(msg);
                System.out.println("Sent message");
            }

        } catch (MessagingException e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>("Sent", HttpStatus.OK);

    }

    @GetMapping(path = "/user/get-role-and-id")
    public ResponseEntity<UserRole> getRoleAndID() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            ArrayList<Authority> authorities = (ArrayList<Authority>) user.getAuthorities();
            UserRole userRole = new UserRole((authorities.get(0).getAuthority()), user);
            return new ResponseEntity<UserRole>(userRole, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/doctor/get-patients/{userName}")
    public ArrayList<Patient> getPatientsForDoctor(@PathVariable String userName) {
        Doctor doctor = (Doctor) userService.findByUsername(userName);
        ArrayList<Patient> returnList = new ArrayList<Patient>();
        Collection<User> patients = userService.getPatients();
        for(User u : patients){
            returnList.add((Patient) u);
        }
        return returnList;
    }
    @GetMapping("/patient/get-doctors/{id}")
    public Set<Doctor> getDoctorsForClinic(@PathVariable long id) {

        Clinic clinic = clinicService.getClinic(id);
        Set<Doctor> doctors = clinic.getDoctors();
        System.out.println(doctors.toString());
        return doctors;
    }


    @PostMapping(value = "/staff/registration/{type}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStaffMember(@RequestBody User user, @PathVariable String type) throws Exception {
        String s = userService.createStaffMember(user, type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/user/delete/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable String userName) {
        Doctor d = (Doctor) userService.findByUsername(userName);
      clinicDoctorRepository.deleteById(d.getId());
        return new ResponseEntity<String>("", HttpStatus.OK);

    }

    @GetMapping(path = "/user/patients",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getPatients() {
        Collection<User> listOfPatients = userService.getPatients();
        return new ResponseEntity<Collection<User>>(listOfPatients, HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable("id") long id) {

        return userRepository.findById(id).map(user -> {
            LOG.info("Reading user with id " + id + " from database.");
            return user;
        }).orElseThrow(() -> new UserNotFoundException("The user with the id " + id + " couldn't be found in the database."));
    }

    @PostMapping(value = "/patient/registration/{type}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPatient(@RequestBody Patient patient, @PathVariable String type) throws Exception {
        String s = userService.createPatient(patient, type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/clinicadmin/registration/{type}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicAdmin(@RequestBody User user, @PathVariable String type) throws Exception {
        String s = userService.createClinicAdmin(user, type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/clinicadmin/regagain/{type}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicAdminAgain(@RequestBody User user, @PathVariable String type) throws Exception {
        String s = userService.createClinicAdminAgain(user, type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/clinicadmin/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getAllCAs() {
        Collection<User> cas = userService.getAllCAs();
        return new ResponseEntity<Collection<User>>(cas, HttpStatus.OK);
    }

    @PostMapping(value = "/cliniccenteradmin/registration/{type}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicCenterAdmin(@RequestBody User user, @PathVariable String type) throws Exception {
        String s = userService.createClinicCenterAdmin(user, type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/cliniccenteradmin/regagain/{type}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicCenterAdminAgain(@RequestBody User user, @PathVariable String type) throws Exception {
        String s = userService.createClinicCenterAdminAgain(user, type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/cliniccenteradmin/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getAllCCAs() {
        Collection<User> ccas = userService.getAllCCAs();
        return new ResponseEntity<Collection<User>>(ccas, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/change-clinic-id/{userName}/{newID}")
    public ResponseEntity<String> createClinicCenterAdminAgain(@PathVariable String userName,@PathVariable Long newID) throws Exception {
        ClinicAdmin s = (ClinicAdmin) userService.findByUsername(userName);
        ClinicAdmin cl = new ClinicAdmin(s.getFirstName(),s.getLastName(),s.getEmail(),s.getUsername(),s.getPassword());
        cl.setClinicName(newID);
        for(Authority a : s.getAuthorities()){
            cl.setAuthorities(a);
        }
        userService.deleteUser(s.getUsername());
        userRepository.save(cl);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @PostMapping(value = "/user/admin-edit/{oldUserName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editAdmin(@RequestBody User clinicAdmin,@PathVariable String oldUserName){
        Optional<User> u  = userRepository.findById(clinicAdmin.getId());
        String s = null;
        if(u.isPresent()){
        User user = u.get();
            User u2 = userService.findByUsername(clinicAdmin.getUsername());
            if(u2 == null){
                return new ResponseEntity<User>(user, HttpStatus.NOT_ACCEPTABLE);
            }else{
                user.setEmail(clinicAdmin.getEmail());
                user.setFirstName(clinicAdmin.getFirstName());
                user.setLastName(clinicAdmin.getLastName());
                user.setUserName(clinicAdmin.getUsername());
                userRepository.save(user);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @PostMapping(value = "/user/doctor-edit/{oldUserName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editDoctor(@RequestBody User clinicAdmin,@PathVariable String oldUserName){
        Optional<User> u  = userRepository.findById(clinicAdmin.getId());
        String s = null;
        if(u.isPresent()){
            User user = u.get();
            User u2 = userService.findByUsername(clinicAdmin.getUsername());
            if(u2 == null){
                return new ResponseEntity<User>(user, HttpStatus.NOT_ACCEPTABLE);
            }else{
                user.setEmail(clinicAdmin.getEmail());
                user.setFirstName(clinicAdmin.getFirstName());
                user.setLastName(clinicAdmin.getLastName());
                user.setUserName(clinicAdmin.getUsername());
                if(!clinicAdmin.getPassword().equals("")){
                    user.setPassword(clinicAdmin.getPassword());
                }
                userRepository.save(user);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }
    @PostMapping(value = "/user/patient-edit/{oldUserName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editPatient(@RequestBody Patient patient,@PathVariable String oldUserName){
        Optional<User> u  = userRepository.findById(patient.getId());
        String s = null;
        if(u.isPresent()){
            User user = u.get();
            User u2 = userService.findByUsername(patient.getUsername());
            if(u2 != null){
                user.setEmail(patient.getEmail());
                user.setFirstName(patient.getFirstName());
                user.setLastName(patient.getLastName());
                user.setUserName(patient.getAddress());
                user.setUserName(patient.getCity());
                user.setUserName(patient.getCountry());
                user.setUserName(patient.getPhoneNumber());
                userRepository.save(user);
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }else{
                return new ResponseEntity<User>(user, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @PostMapping(value = "/user/patient-change-username/{userName}/{oldUserName}")
    public ResponseEntity<?> changePatientUserName(@PathVariable String userName,@PathVariable String oldUserName){
        User user = userService.findByUsername(userName);
        if(user != null){
            return new ResponseEntity<String>("Existing Username!", HttpStatus.NOT_ACCEPTABLE);
        }else {
            User user2 = userService.findByUsername(oldUserName);
            user2.setUserName(userName);
            userRepository.save(user2);
        }
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @PostMapping(value = "/user/admin-change-username/{userName}/{oldUserName}")
    public ResponseEntity<?> changeUserName(@PathVariable String userName,@PathVariable String oldUserName){
        User user = userService.findByUsername(userName);
        if(user != null){
            return new ResponseEntity<String>("Existing Username!", HttpStatus.NOT_ACCEPTABLE);
        }else {
            User user2 = userService.findByUsername(oldUserName);
            user2.setUserName(userName);
            userRepository.save(user2);
        }
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @PostMapping(value = "/user/create-doctor/{clinicID}")
    public ResponseEntity<?> createDoctor(@RequestBody User doctor,@PathVariable Long clinicID){
       User u = (userService.findByUsername(doctor.getUsername()));

        if(u != null){
            return new ResponseEntity<String>("Exists!", HttpStatus.OK);
        }
        Authority doctorAuthority = new Authority();
        doctorAuthority.setName(String.valueOf(UserAuthorities.DOCTOR));
        Doctor d2 = new Doctor();

        d2.setPassChanged(false);
        d2.setPassword(bc.encode(doctor.getPassword()));
        d2.setEmail(doctor.getEmail());
        d2.setUserName(doctor.getUsername());
        d2.setFirstName(doctor.getFirstName());
        d2.setLastName(doctor.getLastName());
        d2.setListOfAppoitnements(new ArrayList<String>());
        d2.setListOfPatients(new ArrayList<Long>());
        d2.setListOfTerms(new ArrayList<String>());
        d2.getAuthorities().add(doctorAuthority);
        userRepository.save(d2);
        User getNew = userService.findByUsername(d2.getUsername());
        Clinic_Doctors cd = new Clinic_Doctors(clinicID,getNew.getId());
        clinicDoctorRepository.save(cd);
        return new ResponseEntity<User>(getNew, HttpStatus.OK);
    }

    @RequestMapping(path = "/secured", method = RequestMethod.GET)
    public @ResponseBody
    String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }



}
