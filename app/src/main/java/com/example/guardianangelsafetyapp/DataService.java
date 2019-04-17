package com.example.guardianangelsafetyapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class DataService extends Service {
    private String link = "https://api.duckduckgo.com/?q=Search&format=json&atb=v1-1";
    private URL url = new URL(link);

    public DataService() throws MalformedURLException {
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public void onCreate(){
        Toast.makeText(this, "Data service started!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId){
        super.onStart(intent, startId);
        try {
            pushData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void pushData() throws IOException, InterruptedException {
        while(true) {
            Toast.makeText(this, "Entered pushData() loop", Toast.LENGTH_LONG).show();
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
            } finally {
                urlConnection.disconnect();
            }
            TimeUnit.SECONDS.sleep(60);
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        System.out.println(sb);
        return sb.toString();
    }
}