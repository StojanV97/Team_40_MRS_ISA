package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.RequestRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import de.jonashackt.springbootvuejs.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;


    @Override
    public String createRequest(RegisterRequests request){
        //808 - korisnik sa tim korisnickim imenom vec postoji
        // 800 - zahtev za registraciju upisan
        // 801 - zaobisao uslove
        String response = "801";
        if(requestRepository.findByUserName(request.getUserName()) != null){
            return "808";
        }
        if(requestRepository.findByEmail(request.getEmail()) != null){
            return "808";
        }
        if(requestRepository.findByInsuranceNumber(request.getInsuranceNumber()) != null){
            return "808";
        }
        if(userRepository.findByUserName(request.getUserName()) != null){
            return "808";
        }
        if(userRepository.findByEmail(request.getEmail()) != null){
            return "808";
        }


        requestRepository.save(new RegisterRequests(request.getFirstName(), request.getLastName(),request.getEmail()
                        ,request.getUserName(),request.getCountry(),request.getCity(),request.getAddress()
                        ,request.getPhoneNumber(),request.getInsuranceNumber()  ));
        response = "800";

        return response;

    }

    @Override
    public String delete(String request) {
        System.out.println(request);
        RegisterRequests rg = requestRepository.findByUserName(request);
        System.out.println(rg);
       requestRepository.delete(rg);
       return "Deleted";
    }

    @Override
    public Collection<RegisterRequests> getAllRequests() {
        return (Collection<RegisterRequests>) requestRepository.findAll();
    }
}

    
