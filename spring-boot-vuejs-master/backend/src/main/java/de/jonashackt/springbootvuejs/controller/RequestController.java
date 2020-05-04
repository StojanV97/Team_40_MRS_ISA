package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.domain.Room;
import de.jonashackt.springbootvuejs.repository.RequestRepository;
import de.jonashackt.springbootvuejs.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
@RequestMapping("/api")
public class RequestController {


    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @PostMapping(value = "/request/registration",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    private ResponseEntity<String> deleteRoom(@PathVariable String userName){
        String s = requestService.delete(userName);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }
}