package com.example.guardianangelsafetyapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String link = "https://gas-device.appspot.com/";
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
            pushData(intent);
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


    public void pushData(Intent intent) throws IOException, InterruptedException {
        while(true) {
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            String result = "";
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = readStream(in);
            } catch(Exception e) {
                ResultReceiver rec = intent.getParcelableExtra("receiverTag");
                Bundle b = new Bundle();
                b.putString("json", "Failed");
                rec.send(0, b);
            } finally {
                urlConnection.disconnect();
            }
            ResultReceiver rec = intent.getParcelableExtra("receiverTag");
            /* For testing
            JSONObject test = new JSONObject();
            try {
                test.put("pressure_data", "100");
                test.put("temperature_data", "100");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result = test.toString();
            */
            Log.d("GAS", "received");
            Bundle b = new Bundle();
            if(result != null) {
                b.putString("json", result);
                rec.send(0, b);
                TimeUnit.SECONDS.sleep(1);
            }
            else{
                System.out.println("Failed data pull");
                b.putString("json", "Failed");
                rec.send(0, b);
            }
        }
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        String incoming = sb.toString();
        try {
            //List<String> responseList = new ArrayList<String>(Arrays.asList(incoming.split(",")));
            //String json = responseList.get(0);
            //System.out.println(json);
            return incoming;
        } catch (Exception e) {
            return incoming;
        }
    }

}