package com.androidhuman.example.simplecontacts;

import android.app.Application;

import io.realm.Realm;

public class SimpleContactsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
