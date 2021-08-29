package it.units.ceschia.help.entity;

import java.util.ArrayList;

public class UserContact {

    ArrayList<Contact> contacts;

    public UserContact() {
    }

    public UserContact(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

}
