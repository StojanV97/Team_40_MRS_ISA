package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.DaysOff;
import org.springframework.data.repository.CrudRepository;

public interface DaysOffRepository extends CrudRepository<DaysOff,Long> {
}
