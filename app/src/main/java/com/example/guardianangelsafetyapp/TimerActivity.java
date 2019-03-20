package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {

    TextView timerText;
    long timeLeft = 15000;
    //120000 milliseconds = 2 minutes

    CountDown timer = new CountDown(timeLeft, 1000);

    public class CountDown extends CountDownTimer{

        public CountDown(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int seconds = (int) millisUntilFinished/1000;
            int minutes = seconds/60;
            seconds = seconds % 60;
            timerText.setText(String.format("%d:%02d", minutes, seconds));
            timeLeft = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            Button timerButton = (Button)findViewById(R.id.timerButton);
            timerButton.setText("Start");
            timerText.setText("2:00");
            timeLeft = 15000;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        timerText = (TextView) findViewById(R.id.timerText);
        Button timerButton = (Button) findViewById(R.id.timerButton);

        timerButton.setText("Start");
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Button timerButton = (Button) v;
                if (timerButton.getText().equals("Stop")){
                    timer.cancel();
                    timerButton.setText("Start");
                } else {
                    timer = new CountDown(timeLeft, 1000);
                    timer.start();
                    timerButton.setText("Stop");
                }
            }
        });
    }
}