package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.service.UserService;
import de.jonashackt.springbootvuejs.service.impl.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@RestController()
@RequestMapping("/api")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static Properties properties = new Properties();
    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    Random r = new Random();

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @PostMapping(value = "/email/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String email) throws MessagingException {
        System.out.println(email);
        final String username = "team40mrsisa@gmail.com";
        final String password = "no0013144";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("team40mrsisa@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject("Activation Code");
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("asdasdwqwekwdknk123nkawdwo");
            emailContent.addBodyPart(textBodyPart);
            //Attach multipart to message
            msg.setContent(emailContent);
            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            System.out.println(e);
        }
        return new ResponseEntity<String>("Sent", HttpStatus.OK);

    }

    @GetMapping(path = "/user/get-role-and-id")
    public ResponseEntity<RoleId> getRoleAndID() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);

            // ne znam sta se desava treba srediti
            List<Authority> authorities = (List<Authority>) user.getAuthorities();
            Long userID = user.getId();

            RoleId roleID;
           /* if (authorities.get(0).getName().equals(UserAuthorities.PATIENT.toString())) {
                ClinicAdmin ca = (Patient)ca;
                roleID =  new RoleId(authorities.get(0).getName(), ca.getId(), ca.getClinicId().getId());
            } else if (authorities.get(0).getName().equals(UserAuthorities.CLINIC_CENTER_ADMIN.toString())) {
                ClinicCenterAdmin cca = (ClinicCenterAdmin) cca;
                roleID = new RoleId(authorities.get(0).getName(), cca.getId(), cca.getClinicCenter().getId());
            } else {
                roleID = new RoleId(authorities.get(0).getName(), userID);
            }*/
            roleID = new RoleId(authorities.get(0).getName(), userID);
            return new ResponseEntity<>(roleID, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    @PostMapping(value = "/staff/registration/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStaffMember(@RequestBody User user,@PathVariable String type) throws Exception {
        String s = userService.createStaffMember(user,type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/user/delete/{userName}")
    public ResponseEntity<String>  deleteUser(@PathVariable String userName) {
        String s = userService.deleteUser(userName);
        return new ResponseEntity<String>(s, HttpStatus.OK);

    }

    @GetMapping(path = "/user/patients")
    public ResponseEntity<Collection<User>> getPatients(){
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

    @PostMapping(value = "/patient/registration/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPatient(@RequestBody User user,@PathVariable String type) throws Exception {
        String s = userService.createPatient(user,type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/clinicadmin/registration/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicAdmin(@RequestBody User user,@PathVariable String type) throws Exception {
        String s = userService.createClinicAdmin(user,type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/clinicadmin/regagain/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicAdminAgain(@RequestBody User user,@PathVariable String type) throws Exception {
        String s = userService.createClinicAdminAgain(user,type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/clinicadmin/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getAllCAs() {
        Collection<User> cas = userService.getAllCAs();
        return new ResponseEntity<Collection<User>>(cas, HttpStatus.OK);
    }

    @PostMapping(value = "/cliniccenteradmin/registration/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicCenterAdmin(@RequestBody User user,@PathVariable String type) throws Exception {
        String s = userService.createClinicCenterAdmin(user,type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(value = "/cliniccenteradmin/regagain/{type}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createClinicCenterAdminAgain(@RequestBody User user,@PathVariable String type) throws Exception {
        String s = userService.createClinicCenterAdminAgain(user,type);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/cliniccenteradmin/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getAllCCAs() {
        Collection<User> ccas = userService.getAllCCAs();
        return new ResponseEntity<Collection<User>>(ccas, HttpStatus.OK);
    }

    @RequestMapping(path="/secured", method = RequestMethod.GET)
    public @ResponseBody String getSecured() {
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
