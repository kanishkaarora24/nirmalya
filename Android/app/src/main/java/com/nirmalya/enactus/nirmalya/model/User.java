package com.nirmalya.enactus.nirmalya.model;

import java.util.List;

public class User {


    /* This is a model class for a user object.
    It will help in mapping to a child of the 'users'
    node in the database.
    */
    private String name;
    private String email;
    private String phone;
    private String address;
    private String accountType;
    private boolean isPremiumUser;
    private boolean meetingRequested;
    private List<Task> listOfTasks;
    private String installDate;
    private String trainingDate;
    private String nextMonitoringVisit;
    private boolean isVisitDone;
    private boolean isProposalSelected;
    private boolean isOrderManufactured;
    private boolean isInstallationDone;
    private String fcm_token;

    public User() {
        // Required empty public constructor for Firebase
    }

    public User(String name, String email, String phone, String address, String accountType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.accountType = accountType;
        this.isPremiumUser = false;
        this.meetingRequested = false;
        this.isTrainerVisitComplete = false;
        this.isOrderManufactured = false;
        this.isProposalSelected = false;
        this.isVisitDone = false;
    }

    public boolean isPremiumUser() {
        return isPremiumUser;
    }

    public void setPremiumUser(boolean premiumUser) {
        isPremiumUser = premiumUser;
    }

    public boolean isMeetingRequested() {
        return meetingRequested;
    }

    public void setMeetingRequested(boolean meetingRequested) {
        this.meetingRequested = meetingRequested;
    }

    public List<Task> getListOfTasks() {
        return listOfTasks;
    }

    public void setListOfTasks(List<Task> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(String trainingDate) {
        this.trainingDate = trainingDate;
    }

    public String getNextMonitoringVisit() {
        return nextMonitoringVisit;
    }

    public void setNextMonitoringVisit(String nextMonitoringVisit) {
        this.nextMonitoringVisit = nextMonitoringVisit;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

    private List<Query> query;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean isVisitDone() {
        return isVisitDone;
    }

    public void setVisitDone(boolean visitDone) {
        isVisitDone = visitDone;
    }

    public boolean isProposalSelected() {
        return isProposalSelected;
    }

    public void setProposalSelected(boolean proposalSelected) {
        isProposalSelected = proposalSelected;
    }

    public boolean isOrderManufactured() {
        return isOrderManufactured;
    }

    public void setOrderManufactured(boolean orderManufactured) {
        isOrderManufactured = orderManufactured;
    }

    public boolean isTrainerVisitComplete() {
        return isTrainerVisitComplete;
    }

    public void setTrainerVisitComplete(boolean trainerVisitComplete) {
        isTrainerVisitComplete = trainerVisitComplete;
    }

    private boolean isTrainerVisitComplete;

    public boolean isInstallationDone() {
        return isInstallationDone;
    }

    public void setInstallationDone(boolean installationDone) {
        isInstallationDone = installationDone;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @Override
    public String toString() {
        return "Name : " +this.getName() + "\n"
                + "Email: " + this.getEmail() + "\n"
                + "Address: " + this.getAddress() +" \n"
                + "Account Type: " + this.getAccountType() + "\n"
                + "Phone: " + this.getPhone() + "\n"
                + "Is Meeting Requested: " + this.isMeetingRequested() + "\n"
                + "Is Visit Done: " + this.isVisitDone() + "\n"
                + "Is Proposal Selected: " + this.isProposalSelected() + "\n"
                + "Is User premium: " + this.isPremiumUser() + "\n"
                + "Is Installation Done: " + this.isInstallationDone() + "\n"
                + "Is Trainer Visit Complete: " + this.isTrainerVisitComplete() + "\n"
                + "FCM Token: " + this.getFcm_token();
        }
}
