package com.example.musicplayer.Base;

import android.app.Application;

import com.example.musicplayer.DataBase.DBManager;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(this);
    }
}
