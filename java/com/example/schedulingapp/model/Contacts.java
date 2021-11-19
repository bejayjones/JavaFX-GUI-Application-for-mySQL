package com.example.schedulingapp.model;

public class Contacts {
    private int contactId;
    private String contactName;

    public Contacts(int contactId) {
        this.contactId = contactId;
    }
    public Contacts(int contactId, String contactName){this.contactId = contactId; this.contactName = contactName;}

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
