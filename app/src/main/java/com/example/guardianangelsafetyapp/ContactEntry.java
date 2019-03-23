package com.example.guardianangelsafetyapp;


public class ContactEntry
{
    private int id;
    private String name;
    private String number;

    public ContactEntry()
    {
        this.id = -1;
        this.name = "";
        this.number = "";
    }

    public ContactEntry(String name)
    {
        this.id = -1;
        this.name = name;
        this.number = "";
    }

    public ContactEntry(String name, String number)
    {
        this.id = id;
        this.name = name;
        this.number = "";
    }


    public ContactEntry(int id, String name, String number)
    {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public String getName() { return name; }
    public String getNumber() { return number; }
    public int getId() { return id; }
}