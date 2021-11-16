package com.example.schedulingapp.model;


import java.time.LocalDate;
import java.util.Date;

public class Appointments {
    private int appointmentId;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    //private Date appointmentDate;
    private String appointmentStartDate;
    private String appointmentEndDate;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointments(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation,
                        String appointmentType, String appointmentStartDate,
                        String appointmentEndDate, int customerId, int userId, int contactId) {

        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        //this.appointmentDate = appointmentDate;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public void setAppointmentStartDate(String appointmentStart) {
        this.appointmentStartDate = appointmentStartDate;
    }

    public String getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public void setAppointmentEndDate(String appointmentEndTime) {
        this.appointmentEndDate = appointmentEndDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
