package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class DataActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        XYSeries series = new XYSeries("Temperature Data Feed");
        Random r = new Random();
        int data;
        int from = 75;
        int to = 90;
        for(int i = 0; i<100; i++){
            data = from + r.nextInt(to - from + 1);
            series.add(i, data);
        }
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.BLUE);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        mRenderer.setPanEnabled(false, false);
        mRenderer.setYAxisMax(35);
        mRenderer.setYAxisMin(0);
        mRenderer.setShowGrid(true);

        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);
        chartContainer.removeAllViews();
        GraphicalView chart = ChartFactory.getBarChartView(getParent(), dataset, mRenderer, BarChart.Type.DEFAULT);
        chartContainer.addView(chart);
    }

}
