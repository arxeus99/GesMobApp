package com.valentelmadafaka.gesmobapp;

import android.app.Application;
import android.content.Context;

import com.valentelmadafaka.gesmobapp.utils.bd.GesMobDB;

public class GesMobApp extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        GesMobApp.context = getApplicationContext();
    }


    public static Context getAppContext(){ return GesMobApp.context; }
}
