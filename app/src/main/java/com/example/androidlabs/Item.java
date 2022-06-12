package com.example.androidlabs;

public class Item {

    String text;
    int urgent;
    long id;


    public Item(String text, int urgent, long id) {
        this.text = text;
        this.urgent = urgent;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int isUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public void update(String t, int u) {
        text = t;
        urgent = u;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

