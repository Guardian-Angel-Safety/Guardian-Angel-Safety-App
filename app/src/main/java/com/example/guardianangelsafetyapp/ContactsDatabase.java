package com.example.guardianangelsafetyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import java.lang.Exception;

public class ContactsDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ContactsDB";
    private static final String TABLE_NAME = "contactsinfo";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUMBER = "number";

    private static ContactsDatabase instance;

    public static void initializeDatabase(Context context) throws Exception {
        if (context == null) {
            throw new Exception("You're an idiot");
        }
        if (instance == null) {
            instance = new ContactsDatabase(context);
        }
    }

    public static ContactsDatabase getInstance() {
        return instance;
    }

    private ContactsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTbl = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_NUMBER + " TEXT)";
        db.execSQL(createTbl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private ContactEntry createContactFromCursor(Cursor cursor) {
        return new ContactEntry(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
        );
    }

    public List<ContactEntry> getContacts() {
        SQLiteDatabase db = this.getReadableDatabase();

        String queryStm = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(queryStm, null);

        List<ContactEntry> contactList = new ArrayList<>();

        if (cursor.moveToFirst()) //Cursor is actively pointing at a record
        {
            while (!cursor.isAfterLast()) {
                contactList.add(createContactFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return contactList;
    }

    public void addContact(ContactEntry entry) throws Exception {
        if (entry == null || entry.getId() == -1 || entry.getName().isEmpty()) {
            throw new Exception("Fail!");
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, entry.getName());
        values.put(KEY_NUMBER, entry.getNumber());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ContactEntry getContactById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryStm, null);

        ContactEntry entry = new ContactEntry();

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER));
            //entry = new ContactEntry(name);
            entry = new ContactEntry(id, name, number);

        }
        cursor.close();
        db.close();
        return entry;
    }

    public void updateContact(ContactEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, entry.getName());
        values.put(KEY_NUMBER, entry.getName());

        String[] args = new String[]{String.format("%d", entry.getId())};
        db.update(TABLE_NAME, values, String.format("%s=?", KEY_ID), args);
        db.close();
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = new String[]{String.format("%d", id)};
        db.delete(TABLE_NAME, String.format("%s=?", KEY_ID), args);
        db.close();
    }

    public static String getKeyId(){
        return KEY_ID;
    }
}