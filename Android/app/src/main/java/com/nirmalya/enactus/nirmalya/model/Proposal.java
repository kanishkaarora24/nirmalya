package com.nirmalya.enactus.nirmalya.model;

public class Proposal {

    private Boolean isSelected;
    private String proposalName;
    private String url;
    private Integer numberOfMeshes;
    private Integer numberOfPits;
    private Integer numberOfHomeComposters;


    public Proposal() {
        // Needed empty constructor for Firebase
    }

    public Proposal(String name, String url, Integer numberOfMeshes, Integer numberOfPits, Integer numberOfHomeComposters, Boolean isSelected) {
        this.isSelected = isSelected;
        this.proposalName = name;
        this.url = url;
        this.numberOfMeshes = numberOfMeshes;
        this.numberOfPits = numberOfPits;
        this.numberOfHomeComposters = numberOfHomeComposters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getProposalName() {
        return proposalName;
    }

    public void setProposalName(String proposalName) {
        this.proposalName = proposalName;
    }

    @Override
    public String toString() {
        String builder = proposalName +
                "\n" +
                numberOfMeshes +
                "\n" +
                numberOfPits +
                "\n" +
                numberOfHomeComposters +
                "\n" +
                url;
        return builder;
    }
}
