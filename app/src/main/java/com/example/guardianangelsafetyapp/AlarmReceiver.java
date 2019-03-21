package com.example.guardianangelsafetyapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        //Notifications not working so this is commented out. View NotifService.java for more info
        //Intent notifService = new Intent(context, NotifService.class);
        Toast.makeText(context, "Guardian Angel Safety Alert", Toast.LENGTH_SHORT).show();
        //SmsManager sms = SmsManager.getDefault();
        //commented to save message line
        //sms.sendTextMessage("9043038515", null, "This is an automated safety alert from the Guardian Angel Safety system. The owner has left a child or pet in their vehicle unattended for 2 minutes. Please check on the vehicle.", null, null);
    }
}
