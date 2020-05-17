package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority,Long> {

    Authority findByName(String name);
}
