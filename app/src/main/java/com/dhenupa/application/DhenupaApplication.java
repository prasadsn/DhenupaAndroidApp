package com.dhenupa.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dhenupa.scheduler.SampleAlarmReceiver;
import com.dhenupa.util.SMSUtil;

/**
 * Created by prsn0001 on 11/29/2015.
 */
public class DhenupaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!preferences.getBoolean("alarm_set", false)){
            new SampleAlarmReceiver().setAlarm(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("alarm_set", true);
            editor.commit();
            editor.apply();
        }

    }
}
