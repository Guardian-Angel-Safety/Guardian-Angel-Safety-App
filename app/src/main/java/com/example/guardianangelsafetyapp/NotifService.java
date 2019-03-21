package com.example.guardianangelsafetyapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

public class NotifService extends Service {
    NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

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
        displayNotification();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void displayNotification(){
        Intent mainIntent = new Intent(this, TimerActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, mainIntent, 0);

        Notification.Builder builder = new Notification.Builder(this, "1")
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_settings)
                .setTicker(getString(R.string.notifmsg))
                .setContentTitle(getString(R.string.notiftitle))
                .setContentText(getString(R.string.notifmsg));

        nm.notify(1, builder.build());
        System.out.println("You made it");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Guardian Angle Safety Alert";
            String description = "Safety alerts";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            nm.createNotificationChannel(channel);
        }
    }
}