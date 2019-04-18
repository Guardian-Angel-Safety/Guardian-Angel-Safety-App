package com.example.guardianangelsafetyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEditCarActivity extends AppCompatActivity {
    private EditText nameET;
    private EditText makeET;
    private EditText modelET;
    private EditText colorET;
    private EditText tagET;
    private Button btnDelete;
    private Button btnAddEdit;

    private int carId = -1;
    private int selectedString = -1;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        nameET = (EditText) findViewById(R.id.v_model);
        makeET = (EditText) findViewById(R.id.v_make);
        modelET = (EditText) findViewById(R.id.v_model);
        colorET = (EditText) findViewById(R.id.v_color);
        tagET = (EditText) findViewById(R.id.v_tag);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnAddEdit = (Button) findViewById(R.id.btnAddEditContact);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);
        carId = intent.getIntExtra("id", -1);

        if (isEdit) {
            btnAddEdit.setText(R.string.btn_edit);
            loadContactInfo();
        } else {
            btnAddEdit.setText(R.string.btn_add);
            btnDelete.setVisibility(View.GONE);
        }

    }

    private void loadContactInfo() {
        CarEntry entry = ContactsDatabase.getInstance().getCarById(carId);
        nameET.setText(entry.getName());
        makeET.setText(entry.getMake());
        modelET.setText(entry.getModel());
        colorET.setText(entry.getColor());
        tagET.setText(entry.getTag());
    }

    public void btnDeleteContact(View view) {
        new AlertDialog.Builder(AddEditCarActivity.this).setTitle("Delete Car")
                .setMessage("Are you sure you want to delete: " + nameET.getText().toString()).setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ContactsDatabase.getInstance().deleteCar(carId);
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).show();
    }

    public void btnAddEditClick(View view) {
        try {
            String name = nameET.getText().toString();
            String make = makeET.getText().toString();
            String model = modelET.getText().toString();
            String color = colorET.getText().toString();
            String tag = tagET.getText().toString();

            if (!isEdit) {
                CarEntry entry = new CarEntry(carId, name, make, model, tag, color);
                ContactsDatabase.getInstance().addCar(entry);
            } else {
                CarEntry entry = new CarEntry(carId, name, make, model, tag, color);
                ContactsDatabase.getInstance().updateCar(entry);
            }
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
