package de.jonashackt.springbootvuejs.joinedtables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLINIC_ROOMS")
public class ClinicRooms {


    private Long clinic_ID;
    @Id
    @Column(updatable = false)
    private Long room_ID;

    public ClinicRooms() {
    }


    public ClinicRooms(Long cId, Long r_ID) {
        this.clinic_ID = cId;
        this.room_ID = r_ID;
    }

    public Long getClinic_ID() {
        return clinic_ID;
    }

    public Long getRoom_ID() {
        return room_ID;
    }

    public void setClinic_ID(Long clinic_ID) {
        this.clinic_ID = clinic_ID;
    }

    public void setRoom_ID(Long room_ID) {
        this.room_ID = room_ID;
    }
}
