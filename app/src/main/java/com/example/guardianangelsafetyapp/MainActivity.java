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

  ImageView pres_icon;
  ImageView bluetooth_icon;
  ImageView battery_icon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Intent dataService = new Intent(this, DataService.class);
    initService(dataService);

    temptext = findViewById(R.id.ui_temptext);
    ring = findViewById(R.id.ui_ring);
    pres_icon = findViewById(R.id.ui_pres);
    bluetooth_icon = findViewById(R.id.ui_bluetooth);
    battery_icon = findViewById(R.id.ui_battery);

    final Button DataButton = findViewById(R.id.DataButton);
    final Button ContactsButton = findViewById(R.id.ContactsButton);
    final Button test = findViewById(R.id.test);

    DataButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, DataActivity.class));
      }
    });

    ContactsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
      }
    });

    test.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setUI(temperature, pressure,30,false);
      }
    });

  }

  @Override
  public void onReceiveResult(int resultCode, Bundle resultData) throws JSONException {
    System.out.println(resultData);
    JSONObject sensorData = new JSONObject(resultData.toString());
    pressure = sensorData.get("pressure").toString();
    temperature = sensorData.get("temperature").toString();
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
    /*
    if (temp > 95f)
    {
      ring.getDrawable().setTint(getResources().getColor(R.color.red_supporting));
    }
    else if (temp > 80f)
    {
      ring.getDrawable().setTint(getResources().getColor(R.color.yellow_supporting));
    }
    else {
      ring.getDrawable().setTint(getResources().getColor(R.color.green_supporting));
    }
    */
    Log.d("myTag", "This is my message");
  }

  public void setPres(String pres)
  {
    Float newPres = Float.parseFloat(pres);
    if (newPres > 5f)
    {
      pres_icon.setImageResource(R.drawable.seatfull);
    }
    else
    {
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
