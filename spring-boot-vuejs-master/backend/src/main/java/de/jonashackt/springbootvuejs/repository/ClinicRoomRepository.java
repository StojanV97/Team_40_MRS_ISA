package de.jonashackt.springbootvuejs.repository;

import de.jonashackt.springbootvuejs.joinedtables.ClinicRooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRoomRepository extends JpaRepository<ClinicRooms,Long> {

}
