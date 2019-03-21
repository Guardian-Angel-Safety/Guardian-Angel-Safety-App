package com.example.guardianangelsafetyapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.os.Build;
import android.graphics.Color;

public class AlarmReceiver extends BroadcastReceiver {

    private static int MID;
    @Override
    public void onReceive(Context context, Intent intent){
        //Notifications not working so this is commented out. View NotifService.java for more info
        //Intent notifService = new Intent(context, NotifService.class);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "guardian_angel_channel";
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
    
            // Configure the notification channel.
            notificationChannel.setDescription("GAS Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    
    
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
    
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_settings)
           //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("Guardian Angel Safety Alert")
                .setContentText("Hey moron, go check on your kid!")
                .setContentInfo("Info");
    
        notificationManager.notify(1, notificationBuilder.build());
        //SmsManager sms = SmsManager.getDefault();
        //commented to save message line
        //sms.sendTextMessage("9043038515", null, "This is an automated safety alert from the Guardian Angel Safety system. The owner has left a child or pet in their vehicle unattended for 2 minutes. Please check on the vehicle.", null, null);
    }
}
