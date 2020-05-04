package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Clinic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClinicRepository extends CrudRepository<Clinic, Long> {
    Clinic findById(@Param("id") long id);
}
