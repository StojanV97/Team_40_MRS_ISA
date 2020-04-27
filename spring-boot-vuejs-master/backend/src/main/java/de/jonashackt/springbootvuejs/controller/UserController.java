package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.exception.UserNotFoundException;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @RequestMapping(path = "/user/{lastName}/{firstName}/{email}/{userName}/{password}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser (@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName,@PathVariable("firstName") String email,@PathVariable("firstName") String userName,@PathVariable("firstName") String password) {
        User savedUser = userRepository.save(new User(firstName, lastName,email,userName,password));

        LOG.info(savedUser.toString() + " successfully saved into DB");

        return savedUser.getId();
    }

    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable("id") long id) {

        return userRepository.findById(id).map(user -> {
            LOG.info("Reading user with id " + id + " from database.");
            return user;
        }).orElseThrow(() -> new UserNotFoundException("The user with the id " + id + " couldn't be found in the database."));
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
