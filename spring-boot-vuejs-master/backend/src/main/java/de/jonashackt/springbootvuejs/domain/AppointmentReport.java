package de.jonashackt.springbootvuejs.domain;

import javax.persistence.*;

@Entity
@Table(name = "appointmentreports")
public class AppointmentReport {
    @Id
    private Long appointmentReportId;

    private String illness;
    private String Description;
    public AppointmentReport() {
    }

    public AppointmentReport(Long appointmentReportId, String illness, String description) {
        this.appointmentReportId = appointmentReportId;
        this.illness = illness;
        this.Description = description;
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

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
