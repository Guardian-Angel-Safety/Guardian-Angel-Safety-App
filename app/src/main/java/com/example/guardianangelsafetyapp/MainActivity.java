package com.example.guardianangelsafetyapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  TextView temptext;
  ImageView ring;

  ImageView pres_icon;
  ImageView bluetooth_icon;
  ImageView battery_icon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
        setUI(69, 20,30,false);
      }
    });


  }

  public void setTemp(float temp)
  {
    int newtemp = Math.round(temp);
    temptext.setText(Integer.toString(newtemp));
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
    Log.d("myTag", "This is my message");
  }

  public void setPres(float pres)
  {
    if (pres > 5f)
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

  public void setUI(float temp, float pres, float battery, boolean bluetooth)
  {
    setTemp(temp);
    setPres(pres);
    setBattery(battery);
    setBluetooth(bluetooth);
  }

}
