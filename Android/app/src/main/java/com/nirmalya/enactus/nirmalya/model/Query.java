package com.nirmalya.enactus.nirmalya.model;

import java.util.ArrayList;

public class Query {

    public String message;
    public ArrayList<String> photoUrls;
    public boolean isResolved;

    public Query() {
        // Empty constructor required for Firebase
    }

    public Query(String message, ArrayList<String> photos) {
        this.message = message;
        this.photoUrls = photos;
        this.isResolved = false;
    }
}
