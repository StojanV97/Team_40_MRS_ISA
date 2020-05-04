package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.RegisterRequests;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends CrudRepository<RegisterRequests, Long> {


    RegisterRequests findById(@Param("id") long id);
    RegisterRequests findByUserName(@Param("userName") String userName);

    RegisterRequests findByEmail(@Param("email") String email);
}
