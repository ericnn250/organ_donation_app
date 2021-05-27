package com.organdonation.ordon.models;

public class User {

    private String name;
    private String email;
    private String phone;
    private String dob;
    private String address;
    private String user_id;
    private String security_level;
    private String role;

    public User(String name,String email,String dob,String address, String phone,  String user_id, String security_level,String role) {
        this.name = name;
        this.email=email;
        this.address=address;
        this.dob=dob;
        this.phone = phone;
        this.role=role;
        this.user_id = user_id;
        this.security_level = security_level;}

    public User() {

    }
    public User(String names,String phone) {
        this.name=names;
        this.phone=phone;

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", user_id='" + user_id + '\'' +
                ", security_level='" + security_level + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

