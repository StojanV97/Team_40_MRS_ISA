package de.jonashackt.springbootvuejs.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import de.jonashackt.springbootvuejs.domain.Doctor;
import de.jonashackt.springbootvuejs.domain.Nurse;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@RestController()
@RequestMapping("/api")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    Random r = new Random();

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
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
