package com.lyl.boon.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Wing_Li
 * 2016/3/31.
 */
public class MyApp extends Application {

    //用于标记已经打开的app
    public ArrayList<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
