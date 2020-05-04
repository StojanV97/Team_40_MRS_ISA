package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import de.jonashackt.springbootvuejs.domain.User;


public interface RequestService {

     String createRequest(RegisterRequests request);
}
