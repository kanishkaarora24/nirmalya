package com.nirmalya.enactus.nirmalya.model;

public class VisitRequest {

    private String customerUid;
    private String customerName;
    private String customerAddress;
    private String dateOfRequest;
    private String phoneNumber;

    public VisitRequest(String uid, String name, String address, String date, String phone) {
        this.customerUid = uid;
        this.customerName = name;
        this.customerAddress = address;
        this.dateOfRequest = date;
        this.phoneNumber = phone;

    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDateOfRequest() {
        return dateOfRequest;
    }

    public void setDateOfRequest(String dateOfRequest) {
        this.dateOfRequest = dateOfRequest;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
