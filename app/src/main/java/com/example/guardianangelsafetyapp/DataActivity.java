package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

public class DataActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);

        final Button refresh = findViewById(R.id.refresh);
        final GraphView graph = DrawGraph();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshGraph(graph);
                DrawGraph();
            }
        });

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);

    }

    protected GraphView DrawGraph(){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Random r = new Random();
        int from = 75;
        int to = 90;
        ArrayList<DataPoint> points = new ArrayList();
        //Bluetooth code will go here:
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, from + r.nextInt(to - from + 1)),
                new DataPoint(1, from + r.nextInt(to - from + 1)),
                new DataPoint(2, from + r.nextInt(to - from + 1)),
                new DataPoint(3, from + r.nextInt(to - from + 1)),
                new DataPoint(4, from + r.nextInt(to - from + 1)),
        });
        graph.addSeries(series);
        return graph;
    }

    protected void RefreshGraph(GraphView graph){
        graph.removeAllSeries();
    }

}
