package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name = "appointmentreports")
public class AppointmentReport {
    @Id
    private Long appointmentReportId;

    public AppointmentReport() {
    }

    public AppointmentReport(Long arId) {
        this.appointmentReportId = arId;
    }

    public Long getAppointmentReportId() {
        return appointmentReportId;
    }

    public void setAppointmentReportId(Long appointmentReportId) {
        this.appointmentReportId = appointmentReportId;
    }
}
