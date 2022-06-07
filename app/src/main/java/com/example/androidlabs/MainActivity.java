package com.example.androidlabs;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String itemName;

    @Override
    public int getCount() {
        return listviewItems.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;

    }

    @Override
    public View getView(int position, View old, ViewGroup parent) {

        View newView = old;
        LayoutInflater inflater = getLayoutInflater();

        if (newVIew == null) {
            newView = inflater.inflate(R.layout.row_layout, parent, attachToRoot:false);
        }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ListView myList = (ListView) findViewById(R.id.listviewItems);

            
        }
    }
}