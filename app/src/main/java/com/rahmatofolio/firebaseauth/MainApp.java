package com.rahmatofolio.firebaseauth;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.rahmatofolio.firebaseauth.helper.PreferencesHelper;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesHelper.initHelper(this, AppConstants.PREF_NAME);
        FirebaseApp.initializeApp(this);
    }
}
