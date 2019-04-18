package com.example.guardianangelsafetyapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText nameET;
    private EditText numberET;
    private Button btnDelete;
    private Button btnAddEdit;

    private int contactId = -1;
    private int selectedString = -1;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit);

        nameET = (EditText) findViewById(R.id.v_model);
        numberET = (EditText) findViewById(R.id.v_make);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnAddEdit = (Button) findViewById(R.id.btnAddEditContact);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);
        contactId = intent.getIntExtra("id", -1);

        if (isEdit) {
            btnAddEdit.setText(R.string.btn_edit);
            loadContactInfo();
        } else {
            btnAddEdit.setText(R.string.btn_add);
            btnDelete.setVisibility(View.GONE);
        }

    }

    private void loadContactInfo() {
        ContactEntry entry = ContactsDatabase.getInstance().getContactById(contactId);
        nameET.setText(entry.getName());
        numberET.setText(entry.getNumber());
    }

    public void btnDeleteContact(View view) {
        new AlertDialog.Builder(AddEditActivity.this).setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete: " + nameET.getText().toString()).setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ContactsDatabase.getInstance().deleteContact(contactId);
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
            String number = numberET.getText().toString();

            if (!isEdit) {
                ContactEntry entry = new ContactEntry(contactId, name, number);
                ContactsDatabase.getInstance().addContact(entry);
            } else {
                ContactEntry entry = new ContactEntry(contactId, name, number);
                ContactsDatabase.getInstance().updateContact(entry);
            }
            finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
