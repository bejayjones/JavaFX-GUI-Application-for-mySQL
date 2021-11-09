package com.example.schedulingapp.model;

import java.sql.Date;
import java.sql.Time;

public class Appointments {
    private int appointmentId;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private Date appointmentStartDate;
    private Time appointmentStartTime;
    private Date appointmentEndDate;
    private Time appointmentEndTime;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointments(int appointmentId, String appointmentTitle, String appointmentDescription, String appointmentLocation,
                        String appointmentType, Date appointmentStartDate, Time appointmentStartTime, Date appointmentEndDate,
                        Time appointmentEndTime, int customerId, int userId, int contactId) {

        this.appointmentId = appointmentId;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndDate = appointmentEndDate;
        this.appointmentEndTime = appointmentEndTime;
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

    public Date getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public void setAppointmentStartDate(Date appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    public Time getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(Time appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public Date getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public void setAppointmentEndDate(Date appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    public Time getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(Time appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
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
