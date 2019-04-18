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
    private static final String TABLE_NAME_CARS = "carinfo";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAKE = "make";
    private static final String KEY_MODEL = "model";
    private static final String KEY_COLOR = "color";
    private static final String KEY_TAG = "tag";
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
        String createTbl = "CREATE TABLE " + TABLE_NAME +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_NUMBER + " TEXT" +
                ")";
        String createCarTbl = "CREATE TABLE " + TABLE_NAME_CARS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_MAKE + " TEXT" +
                KEY_MODEL + " TEXT" +
                KEY_COLOR + " TEXT" +
                KEY_TAG + " TEXT" +
                ")";
        db.execSQL(createTbl);
        db.execSQL(createCarTbl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CARS);
        onCreate(db);
    }

    private ContactEntry createContactFromCursor(Cursor cursor) {
        return new ContactEntry(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
        );
    }

    private CarEntry createCarFromCursor(Cursor cursor) {
        return new CarEntry(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                cursor.getString(cursor.getColumnIndex(KEY_MAKE)),
                cursor.getString(cursor.getColumnIndex(KEY_MODEL)),
                cursor.getString(cursor.getColumnIndex(KEY_TAG)),
                cursor.getString(cursor.getColumnIndex(KEY_COLOR))
        );
    }

    public List<CarEntry> getCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT * FROM " + TABLE_NAME_CARS;
        Cursor cursor = db.rawQuery(queryStm, null);
        List<CarEntry> carList = new ArrayList<>();
        if (cursor.moveToFirst()) //Cursor is actively pointing at a record
        {
            while (!cursor.isAfterLast()) {
                carList.add(createCarFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return carList;
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
    public void addCar(CarEntry entry) throws Exception {
        if (entry == null || entry.getId() == -1) {
            throw new Exception("Fail!");
        }
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, entry.getName());
        values.put(KEY_MAKE, entry.getMake());
        values.put(KEY_MODEL, entry.getModel());
        values.put(KEY_COLOR, entry.getColor());
        values.put(KEY_TAG, entry.getTag());
        db.insert(TABLE_NAME_CARS, null, values);
        db.close();
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

    public CarEntry getCarById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryStm = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryStm, null);

        CarEntry entry = new CarEntry();

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            String make = cursor.getString(cursor.getColumnIndex(KEY_MAKE));
            String model = cursor.getString(cursor.getColumnIndex(KEY_MODEL));
            String color = cursor.getString(cursor.getColumnIndex(KEY_COLOR));
            String tag = cursor.getString(cursor.getColumnIndex(KEY_TAG));
            //entry = new ContactEntry(name);
            entry = new CarEntry(id, name, make, model, color, tag);
        }
        cursor.close();
        db.close();
        return entry;
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
    public void updateCar(CarEntry entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, entry.getName());
        values.put(KEY_MAKE, entry.getMake());
        values.put(KEY_MODEL, entry.getModel());
        values.put(KEY_COLOR, entry.getColor());
        values.put(KEY_TAG, entry.getTag());
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

    public void deleteCar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = new String[]{String.format("%d", id)};
        db.delete(TABLE_NAME_CARS, String.format("%s=?", KEY_ID), args);
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