package com.skyfree.ring.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by KienBeu on 4/12/2018.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
