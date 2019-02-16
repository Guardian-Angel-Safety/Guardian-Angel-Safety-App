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
import android.content.Intent;

import java.util.List;
import java.util.HashMap;

import java.lang.Exception;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {


    private ExpandableListView expandableListView; //This is our list view of our contacts
    private CustomListAdapter contactsListAdapter; //Custom list adapter for displaying our contacts
    private List<String> expandableListHeader;
    private HashMap<String, List<String>> expandableListDetail;

    public static HashMap<String, String> contactsDictionary;


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
        super.onResume();
        populateContactList();
    }

    private void createBogusContacts() {
        ContactsDatabase db = ContactsDatabase.getInstance();
        try 
        {
            ContactEntry entry = new ContactEntry();
            db.addContact(null); //If contact entry is null, throw exception
            db.addContact(new ContactEntry()); //Contact entry is not null, but malformed
            db.addContact(new ContactEntry(0, null)); //Contact entry has proper id, but no name
            Toast.makeText(getApplicationContext(), "Tests failed!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Tests successful", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateContactList() {

        expandableListDetail = getData();
        expandableListHeader = new ArrayList<String>(expandableListDetail.keySet());
        //Create a new list adapter to store and parse contact data
        contactsListAdapter = new CustomListAdapter(
            this,
            expandableListHeader,
            expandableListDetail
        );

        expandableListView.setAdapter(contactsListAdapter);
    }

    public HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> listItems = new HashMap<>();
        List<ContactEntry> contactsList = ContactsDatabase.getInstance().getContacts(); 
        List<String> bodyText;

        contactsDictionary = new HashMap<>();

        for(ContactEntry ent : contactsList) {
            bodyText = new ArrayList<>();
            bodyText.add(String.format("Name: %s", ent.getName()));
            listItems.put(ent.getName(), bodyText);
            contactsDictionary.put(ent.getName(), String.format("%d", ent.getId()));
        }

        return listItems;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add) {
            Intent intent = new Intent(ContactsActivity.this, AddEditActivity.class);
            intent.putExtra("id", contactsDictionary.size() + 1);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
