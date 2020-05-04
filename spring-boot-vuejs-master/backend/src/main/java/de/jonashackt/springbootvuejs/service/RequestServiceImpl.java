package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.*;
import de.jonashackt.springbootvuejs.repository.RequestRepository;
import de.jonashackt.springbootvuejs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    private RequestRepository requestRepository;



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

        requestRepository.save(new RegisterRequests(request.getFirstName(), request.getLastName(),request.getEmail(),request.getUserName()));
        response = "800";

        return response;

    }
}

    
