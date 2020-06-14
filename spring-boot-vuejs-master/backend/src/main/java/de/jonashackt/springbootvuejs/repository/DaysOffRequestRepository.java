package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.DaysOffRequest;
import org.springframework.data.repository.CrudRepository;

public interface DaysOffRequestRepository extends CrudRepository<DaysOffRequest,Long> {
}
