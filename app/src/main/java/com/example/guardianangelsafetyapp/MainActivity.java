package com.example.guardianangelsafetyapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Button DataButton = findViewById(R.id.DataButton);
    final Button ContactsButton = findViewById(R.id.ContactsButton);
    final Button TimerButton = findViewById(R.id.TimerButton);

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

    TimerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, TimerActivity.class));
      }
    });
  }
}
