package de.jonashackt.springbootvuejs.domain;

public class AppointmentPreviewer {


    private String dateAndTime;
    private String doctorFirstName;
    private String doctorLastName;
    private String clinicName;
    private String clinicAddress;
    private String appointmentType;
    private String diagnosis;
    private long appointmentID;
    private boolean cancelable;
    private String illness;
    private String reportDescription;


    public AppointmentPreviewer(String dateAndTime,String doctorFirstName, String doctorLastName, String clinicName, String clinicAddress, String appointmentType, String diagnosis, long appointmentID) {
        this.dateAndTime = dateAndTime;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.appointmentType = appointmentType;
        this.diagnosis = diagnosis;
        this.appointmentID = appointmentID;
    }

    public AppointmentPreviewer(String dateAndTime, String doctorFirstName, String doctorLastName, String clinicName, String clinicAddress, String appointmentType, String diagnosis, long appointmentID, boolean cancelable, String illness, String reportDescription) {
        this.dateAndTime = dateAndTime;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.appointmentType = appointmentType;
        this.diagnosis = diagnosis;
        this.appointmentID = appointmentID;
        this.cancelable = cancelable;
        this.illness = illness;
        this.reportDescription = reportDescription;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public AppointmentPreviewer() {
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(long appointmentID) {
        this.appointmentID = appointmentID;
    }
}
