package de.jonashackt.springbootvuejs.service;

import de.jonashackt.springbootvuejs.domain.Authority;

import java.util.List;

public interface AuthorityService {

    List<Authority> findByName(String name);
}
