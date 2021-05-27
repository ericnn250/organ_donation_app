package com.organdonation.ordon.models;

public class Donation {
    private String names;
    private String dob;
    private String phone;
    private String address;
    private String bloodtype;
    private String organtype;
    private String note;
    private String status;
    private String userid;
    private String donation_date;
    private String doctorid;
    private String doctorname;
    private String recipientid;
    private String recipientnames;
    private String donationid;

    public Donation(String names, String dob, String phone,
                    String address, String bloodtype, String organtype,
                    String note,String userid,String status,String doctorid,String recipient,String donationid,String recipientnames,String doctorname) {
        this.names = names;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.bloodtype = bloodtype;
        this.organtype = organtype;
        this.note = note;
        this.userid=userid;
        this.status=status;
        this.doctorid=doctorid;
        this.doctorname=doctorname;
        this.recipientid=recipient;
        this.donationid=donationid;
        this.recipientnames=recipientnames;
    }
    public Donation() {
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getOrgantype() {
        return organtype;
    }

    public void setOrgantype(String organtype) {
        this.organtype = organtype;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getRecipientid() {
        return recipientid;
    }

    public void setRecipientid(String recipientid) {
        this.recipientid = recipientid;
    }

    public String getDonationid() {
        return donationid;
    }

    public void setDonationid(String donationid) {
        this.donationid = donationid;
    }

    public String getRecipientnames() {
        return recipientnames;
    }

    public void setRecipientnames(String recipientnames) {
        this.recipientnames = recipientnames;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "names='" + names + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", bloodtype='" + bloodtype + '\'' +
                ", organtype='" + organtype + '\'' +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                ", userid='" + userid + '\'' +
                ", donation_date='" + donation_date + '\'' +
                ", doctorid='" + doctorid + '\'' +
                '}';
    }
}
