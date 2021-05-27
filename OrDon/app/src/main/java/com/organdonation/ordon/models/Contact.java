package com.organdonation.ordon.models;

public class Contact {
    private String id;
    private String uid;
    private String username;
    private String email;
    private String message;
    private String datedone;

    public Contact(String id,String uid, String username, String email, String message, String datedone) {
        this.id = id;
        this.uid=uid;
        this.username = username;
        this.email = email;
        this.message = message;
        this.datedone = datedone;
    }
    public Contact() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatedone() {
        return datedone;
    }

    public void setDatedone(String datedone) {
        this.datedone = datedone;
    }

    @Override
    public String toString() {
        return "contact{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", datedone='" + datedone + '\'' +
                '}';
    }
}
