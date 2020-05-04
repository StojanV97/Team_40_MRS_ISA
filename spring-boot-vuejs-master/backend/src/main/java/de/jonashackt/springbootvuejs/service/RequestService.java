package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.domain.User;

import java.util.Collection;


public interface RequestService {

     String createRequest(RegisterRequests request);

    Collection<RegisterRequests> getAllRequests();

    String delete(String request);


}
