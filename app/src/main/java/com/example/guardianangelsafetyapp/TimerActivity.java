package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TimerActivity extends Activity {

    long timeLeft = 120000; //change this if you need the time to be shorter (it will still say "2:00" unless you change the textview text
    //120000 milliseconds = 2 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        //get UI elements
        Button timerButton = (Button) findViewById(R.id.timerButton);

        timerButton.setText("Start");
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Button timerButton = (Button) v;
                if (timerButton.getText().equals("Stop")){
                    //when you click it when it says stop, stop it and change the text
                    cancelTimer();
                    timerButton.setText("Start");
                } else {
                    //when you click it when it says start, start it with the remaining time and change the text
                    startTimer();
                    timerButton.setText("Stop");
                }
            }
        });
    }

    public void startTimer(){
        int i = 15;
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i*1000), pendingIntent);
        Toast.makeText(this, "Timer set", Toast.LENGTH_LONG).show();
    }

    public void cancelTimer(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 0, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Timer cancelled", Toast.LENGTH_LONG).show();
    }
}
