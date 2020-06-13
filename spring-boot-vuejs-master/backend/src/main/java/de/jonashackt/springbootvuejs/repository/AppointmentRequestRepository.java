package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.AppointmentRequest;
import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AppointmentRequestRepository extends CrudRepository<AppointmentRequest, Long> {

    AppointmentRequest findById(@Param("id") long id);

}
