package com.example.guardianangelsafetyapp;

import android.app.Activity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;
import java.util.HashMap;

import java.lang.Exception;

public class ContactsActivity extends AppCompatActivity {


    private ExpandableListView expandableListView; //This is our list view of our contacts
    private CustomListAdapter contactsListAdapter; //Custom list adapter for displaying our contacts
    private List<String> expandableListHeader;
    private HashMap<String, List<String>> expandableListDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        try
        {
            ContactsDatabase.initializeDatabase(getApplicationContext()); //Initialize our database on create
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }

        expandableListView = (ExpandableListView) findViewById(R.id.mainContent);
    }

    @Override
    protected void onResume() {

    }

    private void populateContactList() {
        //Create a new list adapter to store and parse contact data
        contactsListAdapter = new CustomListAdapter(
            getApplicationContext(),
            expandableListHeader,
            expandableListDetail
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Toast.makeText(getApplicationContext(), "Hello menu world!", Toast.LENGTH_LONG).show();
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
