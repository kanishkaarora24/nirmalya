package com.nirmalya.enactus.nirmalya.model;

public class KnowTeamItem {
    private String mDesignation, mName, mNumber, mEmail;
    private Integer mImageId;


    public KnowTeamItem(String mDesignation, String mName, String mNumber, String mEmail, Integer mImageId) {
        this.mDesignation = mDesignation;
        this.mName = mName;
        this.mNumber = mNumber;
        this.mEmail = mEmail;
        this.mImageId = mImageId;
    }

    public String getdesignation(){ return mDesignation;}
    private void setdesignationn(String item){ this.mDesignation=item; }

    public String getname(){ return mName;}
    private void setname(String item){ this.mName=item; }

    public String getemail(){ return mEmail;}
    private void setemail(String item){ this.mEmail=item; }

    public String getnumber(){ return mNumber;}
    private void setnumber(String item){ this.mNumber=item; }

    public Integer getimageid(){ return mImageId;}
    private void setImage(Integer mImageid){this.mImageId=mImageid;}
}
