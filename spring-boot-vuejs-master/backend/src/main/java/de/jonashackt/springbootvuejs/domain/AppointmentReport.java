package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name = "appointmentreports")
public class AppointmentReport {
    @Id
    private Long appointmentReportId;
    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "medicalRecord_id")
    //private MedicalRecord medicalRecord;
    public AppointmentReport() {
    }

    public Long getAppointmentReportId() {
        return appointmentReportId;
    }

    public void setAppointmentReportId(Long appointmentReportId) {
        this.appointmentReportId = appointmentReportId;
    }

    /*public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }*/
}
