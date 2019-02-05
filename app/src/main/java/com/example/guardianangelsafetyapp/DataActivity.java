package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.os.Bundle;
import java.util.Random;

import org.achartengine.model.XYSeries;

public class DataActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
    }
    XYSeries series = new XYSeries("Temperature Data Feed");
    Random r = new Random();
    int data;
    for(int i = 0; i<100; i++){
        data = r.nextInt();
        series.add(i, data);
    }

}
