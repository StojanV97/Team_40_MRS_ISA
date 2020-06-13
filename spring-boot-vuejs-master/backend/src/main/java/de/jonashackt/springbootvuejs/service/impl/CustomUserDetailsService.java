package de.jonashackt.springbootvuejs.service.impl;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        } else {
            return user;
        }
    }

    // Funkcija pomocu koje korisnik menja svoju lozinku
    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");

            return;
        }

        LOGGER.debug("Changing password for user '" + username + "'");

        User user = (User) loadUserByUsername(username);

        // pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
        // ne zelimo da u bazi cuvamo lozinke u plain text formatu
        user.setPassword(passwordEncoder.encode(newPassword));


        if (user instanceof ClinicAdmin) {
            ClinicAdmin ca = (ClinicAdmin) user;
            ca.setPassChanged(true);
            userRepository.save(ca);
        } else if (user instanceof ClinicCenterAdmin) {
            ClinicCenterAdmin cca = (ClinicCenterAdmin) user;
            cca.setPassChanged(true);
            userRepository.save(cca);
        } else if (user instanceof Doctor) {
            Doctor d = (Doctor) user;
            d.setPassChanged(true);
            userRepository.save(d);
        } else if (user instanceof Nurse) {
            Nurse n = (Nurse) user;
            n.setPassChanged(true);
            userRepository.save(n);
        }
         else if (user instanceof Patient) {
            Patient n = (Patient) user;
            n.setPassChanged(true);
            userRepository.save(n);
        }

    }
}
