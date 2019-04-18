package com.example.guardianangelsafetyapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.health.SystemHealthManager;
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

public class DataService extends IntentService {
    private String link = "https://gas-device.appspot.com/docs/#!/default/rootGET";
    private URL url = new URL(link);

    public DataService() throws MalformedURLException {
        super("DataService");
    }

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            pushData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(){
        System.out.println("Data service created!");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId){
        System.out.println("Data service started!");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    public void pushData() throws IOException, InterruptedException {
        while(true) {
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