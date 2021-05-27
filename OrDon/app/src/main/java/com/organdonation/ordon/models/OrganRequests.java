package com.organdonation.ordon.models;

public class OrganRequests {
    private String names;
    private String dob;
    private String phone;
    private String address;
    private String bloodtype;
    private String organtype;
    private String note;
    private String status;
    private String userid;
    private String requestid;
    private String doctorid;
    private String doctorname;
    private String request_date;
    private String donorid;
    private String donornames;
    private String ref_number;

    public OrganRequests(String names, String dob, String phone, String address,String ref_number,
                         String bloodtype, String organtype, String note,String
                                 userid,String status,String doctorid,String donorid,String request_id,String dnames,String doctorname) {
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
        this.donorid=donorid;
        this.requestid=request_id;
        this.donornames=dnames;
        this.ref_number=ref_number;
    }
    public OrganRequests() {
    }

    public String getRef_number() {
        return ref_number;
    }

    public void setRef_number(String ref_number) {
        this.ref_number = ref_number;
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

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDonorid() {
        return donorid;
    }

    public void setDonorid(String donorid) {
        this.donorid = donorid;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getDonornames() {
        return donornames;
    }

    public void setDonornames(String donornames) {
        this.donornames = donornames;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    @Override
    public String toString() {
        return "OrganRequests{" +
                "names='" + names + '\'' +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", bloodtype='" + bloodtype + '\'' +
                ", organtype='" + organtype + '\'' +
                ", note='" + note + '\'' +
                ", status='" + status + '\'' +
                ", userid='" + userid + '\'' +
                ", doctorid='" + doctorid + '\'' +
                ", request_date='" + request_date + '\'' +
                '}';
    }
}
