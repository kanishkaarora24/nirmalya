package com.nirmalya.enactus.nirmalya;

import com.google.firebase.database.FirebaseDatabase;

public class NirmalyaApp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
