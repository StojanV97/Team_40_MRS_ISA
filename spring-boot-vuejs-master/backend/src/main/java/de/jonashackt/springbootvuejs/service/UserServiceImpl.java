package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.*;

import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public String createStaffMember(User user, String type) {
        //808 - korisnik vec postoji
        // 800 - korisnik upisan
        // 801 - zaobisao uslove
        String response = "801";
        if(userRepository.findByUserName(user.getUserName()) != null){
            return "808";
        }
        if(type.equalsIgnoreCase("Doctor")){
            String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
            userRepository.save(new Doctor(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
            response = "800";
        }else if(type.equalsIgnoreCase("Nurse")){
            String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
            userRepository.save(new Nurse(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
            response = "800";
        }
        return response;

    }


    @Override
    public String deleteUser(String userName) {
        if(userRepository.findByUserName(userName) != null){
            User u = userRepository.findByUserName(userName);
            userRepository.delete(u);
            return "800";
        }
        else{
            return "808";
        }
    }

    @Override
    public String createPatient(User user, String type){
        //808 - korisnik vec postoji
        // 800 - korisnik upisan
        // 801 - zaobisao uslove
        String response = "801";
        if(userRepository.findByUserName(user.getUserName()) != null){
            return "808";
        }

        String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
        userRepository.save(new Patient(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
        response = "800";

        return response;

    }
    @Override
    public String createClinicAdmin(User user, String type){
        //808 - admin sa tim korisnickim imenom vec postoji
        // 800 - admin klinike upisan
        // 801 - zaobisao uslove
        String response = "801";
        if(userRepository.findByUserName(user.getUserName()) != null){
            return "808";
        }

        String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
        userRepository.save(new ClinicAdmin(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
        response = "800";

        return response;

    }

    @Override
    public String createClinicAdminAgain(User user, String type) {
        String response = "801";

        String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
        userRepository.save(new ClinicAdmin(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
        response = "800";

        return response;
    }

    @Override
    public Collection<User> getAllCAs() {
        Collection<User> users = (Collection<User>) userRepository.findAll();
        Collection<User> cas = new ArrayList<>();
        for (User user: users) {
            if(user instanceof ClinicAdmin) {
                cas.add(user);
            }
        }
        return cas;
    }

    @Override
    public String createClinicCenterAdmin(User user, String type) {
        //808 - admin sa tim korisnickim imenom vec postoji
        // 800 - admin klinike upisan
        // 801 - zaobisao uslove
        String response = "801";
        if(userRepository.findByUserName(user.getUserName()) != null){
            return "808";
        }

        String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
        userRepository.save(new ClinicCenterAdmin(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
        response = "800";

        return response;
    }

    @Override
    public String createClinicCenterAdminAgain(User user, String type) {
        String response = "801";

        String s = Long.toHexString(Double.doubleToLongBits(Math.random()));
        userRepository.save(new ClinicCenterAdmin(user.getFirstName(), user.getLastName(),user.getEmail(),user.getUserName(),s));
        response = "800";

        return response;
    }

    @Override
    public Collection<User> getAllCCAs() {
        Collection<User> users = (Collection<User>) userRepository.findAll();
        Collection<User> ccas = new ArrayList<>();
        for (User user: users) {
            if(user instanceof ClinicCenterAdmin) {
                ccas.add(user);
            }
        }
        return ccas;
    }
}
