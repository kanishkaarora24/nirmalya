package com.nirmalya.enactus.nirmalya.model;

public class ManufacturerOrder {

    private Integer numberOfMeshes;
    private Integer numberOfPits;
    private Integer numberOfHomeComposters;
    private String customerAddress;
    private String customerContact;
    private String customerName;
    private String estimatedDate;
    private String orderName;

    public ManufacturerOrder() {
        // Empty constructor required for Firebase
    }

    public ManufacturerOrder(Integer meshes, Integer pits, Integer homeComposters, String address, String contact, String name, String orderName) {
        this.numberOfMeshes = meshes;
        this.numberOfPits = pits;
        this.numberOfHomeComposters = homeComposters;
        this.customerAddress = address;
        this.customerContact = contact;
        this.customerName = name;
        this.orderName = orderName;
    }

    public Integer getNumberOfMeshes() {
        return numberOfMeshes;
    }

    public void setNumberOfMeshes(Integer numberOfMeshes) {
        this.numberOfMeshes = numberOfMeshes;
    }

    public Integer getNumberOfPits() {
        return numberOfPits;
    }

    public void setNumberOfPits(Integer numberOfPits) {
        this.numberOfPits = numberOfPits;
    }

    public Integer getNumberOfHomeComposters() {
        return numberOfHomeComposters;
    }

    public void setNumberOfHomeComposters(Integer numberOfHomeComposters) {
        this.numberOfHomeComposters = numberOfHomeComposters;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    @Override
    public String toString() {
        String builder = customerName + "\n" +
                customerAddress + "\n" +
                customerContact + "\n" +
                numberOfMeshes + "\n" +
                numberOfPits + "\n" +
                numberOfHomeComposters + "\n" +
                estimatedDate + "\n";
        return builder;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

}
