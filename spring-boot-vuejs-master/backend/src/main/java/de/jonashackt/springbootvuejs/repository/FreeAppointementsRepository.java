package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.FreeAppointements;
import org.springframework.data.repository.CrudRepository;

public interface FreeAppointementsRepository extends CrudRepository<FreeAppointements,Long> {
}
