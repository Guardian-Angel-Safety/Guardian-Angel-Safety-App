package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {

    //Declare textbox scope where time left is printed
    TextView timerText;
    long timeLeft = 120000; //change this if you need the time to be shorter (it will still say "2:00" unless you change the textview text
    //120000 milliseconds = 2 minutes

    //create countdown object to run from timeLeft and descending by 1000 millis every tick
    CountDown timer = new CountDown(timeLeft, 1000);

    //custom implementation of CountDownTimer to start from variable time
    public class CountDown extends CountDownTimer{

        //start timer from timeLeft
        public CountDown(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //millis to seconds to minutes
            int seconds = (int) millisUntilFinished/1000;
            int minutes = seconds/60;
            seconds = seconds % 60; //keep seconds below 60
            timerText.setText(String.format("%d:%02d", minutes, seconds));
            timeLeft = millisUntilFinished; //update timeLeft
        }

        @Override
        public void onFinish() {
            Button timerButton = (Button)findViewById(R.id.timerButton);
            timerButton.setText("Start"); //change button text from "Stop" to "Start"
            timerText.setText("2:00"); //reset timer text
            timeLeft = 120000;  //reset timeLeft when timer finishes
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        //get UI elements
        timerText = (TextView) findViewById(R.id.timerText);
        Button timerButton = (Button) findViewById(R.id.timerButton);

        timerButton.setText("Start");
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Button timerButton = (Button) v;
                if (timerButton.getText().equals("Stop")){
                    //when you click it when it says stop, stop it and change the text
                    timer.cancel();
                    timerButton.setText("Start");
                } else {
                    //when you click it when it says start, start it with the remaining time and change the text
                    timer = new CountDown(timeLeft, 1000);
                    timer.start();
                    timerButton.setText("Stop");
                }
            }
        });
    }
}
