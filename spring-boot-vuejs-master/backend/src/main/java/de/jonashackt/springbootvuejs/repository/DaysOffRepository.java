package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.DaysOff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DaysOffRepository extends CrudRepository<DaysOff,Long> {

    DaysOff findByUserID(@Param("userID") long userID);
}
