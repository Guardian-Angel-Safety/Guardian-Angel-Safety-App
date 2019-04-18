package com.example.guardianangelsafetyapp;


public class CarEntry
{
    private int id;
    private String name;
    private String make;
    private String model;
    private String tag;
    private String color;

    public CarEntry()
    {
        this.id = -1;
        this.name = "";
        this.make = "";
        this.model = "";
        this.tag = "";
        this.color = "";
    }

    public CarEntry(String name)
    {
        this.id = -1;
        this.name = name;
        this.make = "";
        this.model = "";
        this.tag = "";
        this.color = "";
    }


    public CarEntry(int id, String name, String make, String model, String tag, String color)
    {
        this.id = id;
        this.name = name;
        this.make = make;
        this.model = model;
        this.tag = tag;
        this.color = color;
    }

    public String getName() { return name; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getTag() { return tag; }
    public String getColor() { return color; }
    public int getId() { return id; }
}