package com.example.chens.PlaymySong.ui.settings;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


public class ExitActivity extends Application {
    private List<Activity> activityList = new LinkedList<Activity>();
    private static ExitActivity instance;
    private ExitActivity(){ }
    public static ExitActivity getInstance() {
        if(null == instance) {
            instance = new ExitActivity();
        }
        return instance;
    }

    public void addActivity(Activity activity)  {
        activityList.add(activity);
    }

    public void exit(){
        for(Activity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
