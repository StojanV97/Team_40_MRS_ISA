package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends CrudRepository<Room, Long>{

     Room findByRoomID(@Param("roomID") long roomID);
}
