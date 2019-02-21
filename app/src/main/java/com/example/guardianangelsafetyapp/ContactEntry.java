package com.example.guardianangelsafetyapp;


public class ContactEntry
{
    private int id;
    private String name;

    public ContactEntry()
    {
        this.id = -1;
        this.name = "";
    }

    public ContactEntry(String name)
    {
        this.id = -1;
        this.name = name;
    }

    public ContactEntry(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getName() { return name; }
    public int getId() { return id; }
}