package com.arjuncodes.springemaildemo.Controller;

public class emailMessage {
    private String mail;

    public emailMessage() {
        // Default constructor
    }

    public emailMessage(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
