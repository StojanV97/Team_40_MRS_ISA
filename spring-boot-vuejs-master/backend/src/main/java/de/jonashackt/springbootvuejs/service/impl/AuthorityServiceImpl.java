package de.jonashackt.springbootvuejs.service.impl;

import de.jonashackt.springbootvuejs.domain.Authority;
import de.jonashackt.springbootvuejs.repository.AuthorityRepository;
import de.jonashackt.springbootvuejs.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findByName(String name) {
        Authority auth = authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
