package com.nirmalya.enactus.nirmalya.model;

public class ServiceItem {

    private String mTitle;
    private String mSubtitle;

    public ServiceItem(String title, String subtitle) {
        this.mTitle = title;
        this.mSubtitle = subtitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }
}
