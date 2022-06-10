package com.example.androidlabs;

public class Item {

    String text;
    boolean urgent;

    public Item(String text, boolean urgent){
        this.text = text;
        this.urgent = urgent;
    }

    public String getText() {
        return text;
    }
}

