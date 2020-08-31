package de.jonashackt.springbootvuejs.joinedtables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLINIC_ADMIN")
public class Clinic_Admin {

    private Long Clinic_ID;
    @Id
    @Column(updatable = false)
    private Long Admin_ID;

    public Clinic_Admin() {
    }

    public Clinic_Admin(Long cId, Long aId)
    {
        this.Clinic_ID = cId;
        this.Admin_ID = aId;
    }
    public Clinic_Admin()
    {

    }
    public Long getClinic_ID() {
        return Clinic_ID;
    }

    public Long getAdmin_ID() {
        return Admin_ID;
    }

    public void setClinic_ID(Long clinic_ID) {
        Clinic_ID = clinic_ID;
    }

    public void setAdmin_ID(Long admin_ID) {
        Admin_ID = admin_ID;
    }
}
