package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Patient;
import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


public interface UserService {

     String createStaffMember(User user, String type);
     String deleteUser(String user);
     String createPatient(Patient patient, String type);
     String createClinicAdmin(User user, String type);
     String createClinicAdminAgain(User user, String type);
     Collection<User> getAllCAs();

     User findByUsername(String username);
     String createClinicCenterAdmin(User user, String type);
     String createClinicCenterAdminAgain(User user, String type);
     Collection<User> getAllCCAs();
     Collection<User> getPatients();

}
