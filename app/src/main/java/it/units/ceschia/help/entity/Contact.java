package it.units.ceschia.help.entity;

import java.util.HashMap;

public class Contact {
    private String fbId;
    private String name;
    private String surname;
    private String phone;
    private String mail;
    private String address;
    private String nick;
    private String message;


    private HashMap<String,Application> priorities;

    public Contact() {
    }

    public Contact( String name, String surname, String phone, String mail, String address, String nick, String message, HashMap<String,Application> priorities) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.nick = nick;
        this.message = message;
        this.priorities = priorities;
    }

    public Contact(String name, String surname, String phone, String mail, String address, String nick, String message) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.nick = nick;
        this.message = message;
    }



    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public HashMap<String, Application> getPriorities() {
        return priorities;
    }

    public void setPriorities(HashMap<String, Application> priorities) {
        this.priorities = priorities;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fbId='" + fbId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", nick='" + nick + '\'' +
                ", message='" + message + '\'' +
                ", priorities=" + priorities +
                '}';
    }
}
