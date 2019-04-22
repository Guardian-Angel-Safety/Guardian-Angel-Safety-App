package com.example.guardianangelsafetyapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DataReceiver.Receiver {

  String temperature;
  String pressure;
  TextView temptext;
  ImageView ring;
  DataReceiver mReceiver;
  Intent dataService;

  ImageView pres_icon;
  ImageView bluetooth_icon;
  ImageView battery_icon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    dataService = new Intent(this, DataService.class);
    initService(dataService);

    temptext = findViewById(R.id.ui_temptext);
    ring = findViewById(R.id.ui_ring);
    pres_icon = findViewById(R.id.ui_pres);
    bluetooth_icon = findViewById(R.id.ui_bluetooth);
    battery_icon = findViewById(R.id.ui_battery);

    //final Button DataButton = findViewById(R.id.DataButton);
    final Button ContactsButton = findViewById(R.id.ContactsButton);

    /*
    DataButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        stopService(dataService);
        startActivity(new Intent(MainActivity.this, DataActivity.class));
      }
    });
    */

    ContactsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
      }
    });

  }

  @Override
  public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException {
    try {
      Object unclean = resultData.get("json");
      JSONObject sensorData = new JSONObject(unclean.toString());
      System.out.println(sensorData);
      pressure = sensorData.get("pressure_data").toString();
      temperature = sensorData.get("temperature_data").toString();
      setUI(temperature, pressure, 100f, true);
    } catch (Exception e) {
      System.out.println("Reached catch");
      setUI("NaN", "NaN", 0f, false);
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  public void initService(Intent dataService) {
    mReceiver = new DataReceiver(new Handler());
    mReceiver.setReceiver(this);
    dataService.putExtra("nameTag", "GAS");
    dataService.putExtra("receiverTag", mReceiver);
    startService(dataService);
  }

  public void setTemp(String temp)
  {
    temptext.setText(temp);
    try {
      Float tempF = Float.parseFloat(temp);

      if (tempF > 95f) {
        ring.getDrawable().setTint(getResources().getColor(R.color.red_supporting));
      } else if (tempF > 80f) {
        ring.getDrawable().setTint(getResources().getColor(R.color.yellow_supporting));
      } else {
        ring.getDrawable().setTint(getResources().getColor(R.color.green_supporting));
      }
    } catch (Exception e) {

    }
  }

  public void setPres(String pres)
  {
    try {
      Float newPres = Float.parseFloat(pres);
      if (newPres > 5f) {
        pres_icon.setImageResource(R.drawable.seatfull);
      } else {
        pres_icon.setImageResource(R.drawable.seatempty);
      }
    } catch (Exception e) {
      pres_icon.setImageResource(R.drawable.seatempty);
    }
  }

  public void setBluetooth(boolean on)
  {
    if (on)
    {
      bluetooth_icon.setImageResource(R.drawable.bluetoothconnected);
    }
    else
    {
      bluetooth_icon.setImageResource(R.drawable.bluetoothdisabled);
    }
  }

  public void setBattery(float level)
  {
    if (level > 60f)
    {
      battery_icon.setImageResource(R.drawable.batteryfull);
    }
    else if (level > 25f)
    {
      battery_icon.setImageResource(R.drawable.batteryhalf);
    }
    else
    {
      battery_icon.setImageResource(R.drawable.batterylow);
    }
  }

  public void setUI(String temp, String pres, float battery, boolean bluetooth)
  {
    setTemp(temp);
    setPres(pres);
    setBattery(battery);
    setBluetooth(bluetooth);
  }

}
