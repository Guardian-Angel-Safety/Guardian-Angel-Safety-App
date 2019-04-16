package com.example.guardianangelsafetyapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class DataService extends Service {
    

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId){
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}