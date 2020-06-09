package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.domain.UserTokenState;
import de.jonashackt.springbootvuejs.exception.ResourceConflictException;
import de.jonashackt.springbootvuejs.security.TokenUtils;
import de.jonashackt.springbootvuejs.security.auth.JwtAuthenticationRequest;
import de.jonashackt.springbootvuejs.service.UserService;
import de.jonashackt.springbootvuejs.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws AuthenticationException, IOException {
        try {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
            // Ubaci username + password u kontext
            // SecurityContext klasa cuva podatke trenutno ulogovanog korisnika (principle) , podaci se cuvaju u Authentication objektu
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Kreiraj token
            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername());
            int expiresIn = tokenUtils.getExpiredIn();
            // Vrati token kao odgovor na uspesno autentifikaciju
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        } catch (BadCredentialsException bce) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /*   @RequestMapping(method = POST, value = "/signup")
       public ResponseEntity<?> addUser(@RequestBody RegisterRequests userRequest, UriComponentsBuilder ucBuilder) {

           User existUser = this.userService.findByUsername(userRequest.getUserName());
           if (existUser != null) {
               throw new ResourceConflictException(userRequest.getId(), "Username already exists");
           }

           User user = this.userService.save(userRequest);
           HttpHeaders headers = new HttpHeaders();
           headers.setLocation(ucBuilder.path("/api/user/{userId}").buildAndExpand(user.getId()).toUri());
           return new ResponseEntity<User>(user, HttpStatus.CREATED);
       }
   */
    @RequestMapping(value = "/auth/refresh", method = RequestMethod.POST)
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }
    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}