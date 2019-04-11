package com.example.guardianangelsafetyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
    
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
        SmsManager sms = SmsManager.getDefault();
        //commented to save message line

        /*
        List<String> listNumbers = ContactsActivity.getNumber();

        for (int i = 0; i < listNumbers.size(); i++) {
            sms.sendTextMessage(listNumbers.get(i), null, "This is an automated message from Guardian Angel. A child/pet was left in the vehicle." +
                    "\nPlease contact the owner. If no contact can be made, contact Emergency Services.", null, null);

            System.out.print(listNumbers.get(i));
        }
        */
        //sms.sendTextMessage("7277425492", null, "Alert!", null, null);
    }
}
