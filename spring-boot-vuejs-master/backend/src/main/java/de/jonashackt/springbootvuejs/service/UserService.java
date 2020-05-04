package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.User;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UserService {

     String createStaffMember(User user, String type);
     String deleteUser(String user);

     String createPatient(User user, String type);
     String createClinicAdmin(User user, String type);

     String createClinicCenterAdmin(User user, String type);

}
