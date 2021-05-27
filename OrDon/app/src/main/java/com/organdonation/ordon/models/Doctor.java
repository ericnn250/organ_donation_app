package com.organdonation.ordon.models;

public class Doctor {
    private String names;
    private String email;
    private String contact;
    private String hospital;
    private String adress;
    private String province;
    private String disctrict;
    private String doctor_id;

    public Doctor(String names, String email, String contact, String hospital, String adress, String province, String disctrict) {
        this.names = names;
        this.email = email;
        this.contact = contact;
        this.hospital = hospital;
        this.adress = adress;
        this.province = province;
        this.disctrict = disctrict;
    }

    public Doctor(){

    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDisctrict() {
        return disctrict;
    }

    public void setDisctrict(String disctrict) {
        this.disctrict = disctrict;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}

